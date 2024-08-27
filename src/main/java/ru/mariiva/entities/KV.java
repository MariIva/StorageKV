package ru.mariiva.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Пара ключ значение сущость БД
 * key - ключ таблицы - строка
 * value - значение - строка, конвертируемая из объекта, содержит данные и ttl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KV")
@Builder
public class KV {
    @Id
    @Column(name = "id")
    private String key;
    @Column(name = "data")
    @Convert(converter = ConverterValue.class)
    private Value value;

    /**
     * Метод, отсчитывающий жизнь данных
     */
    public void dicTTL(){
        value.dicTTL();
    }
}
