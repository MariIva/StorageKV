package ru.mariiva;

import ru.mariiva.driver.H2jdbc;
import ru.mariiva.rest.dto.KV_DTO;

import java.sql.SQLException;
import java.util.List;

public class AppDriver {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        H2jdbc driver = new H2jdbc();

        List<KV_DTO> all = driver.getAll();
        all.forEach(System.out::println);

        driver.close();
    }
}
