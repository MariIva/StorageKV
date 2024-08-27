package ru.mariiva.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mariiva.entities.KV;


/**
 * Интерфейс для запросов к БД
 */
public interface KV_DAO  extends JpaRepository<KV, String> {
}
