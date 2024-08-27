package ru.mariiva.entities;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterValueTest {

    private static ConverterValue converter;

    @BeforeAll
    static void create(){
        converter = new ConverterValue();
    }

    @Test
    void convertToDatabaseColumn() {
        Value value = Value.builder().
                data("test").
                ttl(44).
                build();
        String s = converter.convertToDatabaseColumn(value);
        Value v = converter.convertToEntityAttribute(s);
        assertEquals(v, value);
    }

    @Test
    void convertToEntityAttribute() {
        String string = "Value(data=test, ttl=44)";
        Value v = converter.convertToEntityAttribute(string);
        String s = converter.convertToDatabaseColumn(v);
        assertEquals(s, string);
    }
}