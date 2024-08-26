package ru.mariiva.rest.contriller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mariiva.dao.KV_DAO;
import ru.mariiva.entities.KV;
import ru.mariiva.entities.Value;
import ru.mariiva.file.json.Json;
import ru.mariiva.file.json.KVMapper;
import ru.mariiva.rest.dto.KV_DTO;
import ru.mariiva.service.KVService;
import ru.mariiva.service.KVServiceIMPL;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, обработывающий запросы от клиента
 */
@RestController
@RequiredArgsConstructor
public class KVController {
    private final int TTL = 2000;
    private final KVService service;

    /**
     * Метод, получающий все объекты из таблицы
     * @return - список объектов данных из БД
     */
    @GetMapping("/kv")
    public List<KV_DTO> getAll() {
        return service
                .getAll()
                .stream()
                .map(KV_DTO::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Метод, получающий объект из БД по ключу
     * @param key - ключ объекта
     * @return - значение, ассоциированное к ключом
     */
    @RequestMapping(value = "/kv", params = {"key"}, method = RequestMethod.GET)
    public String get(@RequestParam String key){
        return service.getByKey(key);
    }

    /**
     * Метод, добавляющий новую запись в таблицу.
     * Если Ключ уже существует, то обновит значение.
     * Срок жизни записи по умолчанию
     * @param key - ключ
     * @param value -  занные
     * @return - результат добавления
     */
    @RequestMapping(value = "/kv", params = {"key", "value"}, method = RequestMethod.POST)
    public boolean set(@RequestParam String key,
                       @RequestParam String value){
        return service.insert(
                KV.builder().
                        key(key).
                        value(Value.builder().data(value).ttl(TTL).build()).
                        build()
        );
    }

    /**
     * Метод, добавляющий новую запись в таблицу.
     * Если Ключ уже существует, то обновит значение
     * @param key - ключ
     * @param value -  занные
     * @param ttl - срок жизни записи
     * @return - результат добавления
     */
    @RequestMapping(value = "/kv", params = {"key", "value", "ttl"}, method = RequestMethod.POST)
    public boolean set(@RequestParam String key,
                       @RequestParam String value,
                       @RequestParam int ttl){
        return service.insert(
                KV.builder().
                        key(key).
                        value(Value.builder().data(value).ttl(ttl).build()).
                        build()
        );
    }

    /**
     * Метод, удаляющий запись из таблицы по ключу
     * @param key - ключ записи
     * @return - удаленный объект или null, если удаление не удалось
     */
    @DeleteMapping("/kv")
    public KV_DTO remove(@RequestParam String key){
        KV kv = service.deleteByKey(key);
        if (kv != null) {
            return KV_DTO.toDTO(kv);
        }
        else {
            return null;
        }
    }

    /**
     * Сохраняет текущее состояние БД в Json файл
     */
    @GetMapping("/kv/dump")
    public void dump(){
        List<KV> collect = service.getAll();
        Json.toJsonFile(collect);
    }

    /**
     * Обновляет Бд в соответствии с Json файлом.
     * Текущие данные удаляются
     */
    @GetMapping("/kv/load")
    public void load(){
        List<KV> collect = service.getAll();
        collect.stream().parallel().forEach(item->service.deleteByKey(item.getKey()));
        List<KV> list = KVMapper.fromJSONArray(Json.getJsonFromResource());
        list.stream().parallel().forEach(service::insert);
    }
}
