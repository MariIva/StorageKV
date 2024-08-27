package ru.mariiva.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mariiva.dao.KV_DAO;
import ru.mariiva.entities.KV;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KVServiceIMPL implements KVService{
    private final KV_DAO kvDao;

    /**
     * Метод. получающий  список объетов из БД
     * @return - список объектов
     */
    @Override
    public List<KV> getAll() {
        return kvDao.findAll();
    }

    /**
     * Метод, иющий в БД значение по ключу
     * @param key - ключ
     * @return - значение, если ключа не существеть в БД вернет null
     */
    @Override
    public String getByKey(String key) {
        KV kv = kvDao.findById(key).orElse(null);
        if (kv != null) {
            return kv.getValue().getData();
        }
        else {
            return null;
        }
    }

    /**
     * Метод, записывающий  пару ключ-значение в БД
     * @param kv - объетк ключ-значение
     * @return - true - если дабавление успешно, false - добаление провалено
     */
    @Override
    public boolean insert(KV kv) {
        KV save = kvDao.save(kv);
        return save != null;
    }

    /**
     * Метод, удаляющий из бд запись по ключу
     * @param key -  ключ
     * @return - удаленная пара, если удалениене удалось вернет null
     */
    @Override
    public String deleteByKey(String key) {
        KV byId = kvDao.findById(key).orElse(null);
        if (byId == null) {
            return null;
        }
        else {
            kvDao.deleteById(key);
            return byId.getValue().getData();
        }
    }
}
