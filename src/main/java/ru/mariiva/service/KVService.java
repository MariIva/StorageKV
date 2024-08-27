package ru.mariiva.service;

import ru.mariiva.entities.KV;

import java.util.List;

/**
 * Интерфейс, собержащий методы для работы с таблицей БД
 */
public interface KVService {
    List<KV> getAll();
    String getByKey(String key);
    boolean insert(KV kv);
    String deleteByKey(String key);
}
