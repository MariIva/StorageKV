package ru.mariiva.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, хранящий в себе данные и продолжительсть жизнь данных (ttl).
 * data - данные, для хранения в БД
 * ttl - срок жизни данных
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Value {
    private String data;
    private int ttl;

    /**
     * Уменьшает срок жизни зиписи
     */
    protected void dicTTL(){
        ttl--;
    }
}
