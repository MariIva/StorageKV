package ru.mariiva.rest.dto;

import lombok.Builder;
import lombok.Data;
import ru.mariiva.entities.KV;

/**
 * Данные, которые предоставляются клиенту
 */
@Data
@Builder
public class KV_DTO {
    private String key;
    private String value;

    /**
     * Возвращает пару ключ- значение для клиента.
     * TTL отсутствует.
     * @param kv
     * @return
     */
    public static KV_DTO toDTO(KV kv){
        return KV_DTO.builder().
                key(kv.getKey()).
                value(kv.getValue().getData()).
                build();
    }

}
