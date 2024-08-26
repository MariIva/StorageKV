package ru.mariiva.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Класс для конвертации поля "значений":
 *      в стороку, перед отправкой в БД,
 *      в объект, после получения днных из БД
 */
@Converter
public class ConverterValue implements AttributeConverter<Value, String> {
    /**
     * Конвертирует объект в строку
     * @param value - объект для конвертации
     * @return - строка для БД
     */
    @Override
    public String convertToDatabaseColumn(Value value) {
        return value.toString();
    }

    /**
     * Конвертирует стороку в объект
     * @param s - строка из БД
     * @return - объект
     */
    @Override
    public Value convertToEntityAttribute(String s) {
        String start = s.split(",")[0];
        int i1 = start.indexOf("=");
        String end = s.split(",")[1];
        int i2 = end.indexOf("=");
        return Value.builder().
                data((String) start.subSequence(i1 + 1, start.length())).
                ttl(Integer.parseInt((String) end.subSequence(i2 + 1, end.length() - 1))).
                build();
    }
}
