package ru.mariiva;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.mariiva.dao.KV_DAO;
import ru.mariiva.entities.KV;
import ru.mariiva.file.json.Json;
import ru.mariiva.file.json.KVMapper;
import ru.mariiva.service.KVServiceIMPL;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class App {

    private static ConfigurableApplicationContext context, copyc;
    public static void main(String[] args) {
        context = SpringApplication.run(App.class, args);

        try {
            Console.main(args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        startDemon();
        System.out.println("=============================");
    }

    private static void startDemon() {
        Thread daemon = new Thread(() -> {
            while (context != null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    List<KV> list = context.getBean(KV_DAO.class).findAll();
                    list = list.stream().
                            peek(KV::dicTTL).
                            peek(item -> context.getBean(KVServiceIMPL.class).insert(item)).
                            filter(item -> item.getValue().getTtl() < 1).
                            peek(item -> context.getBean(KVServiceIMPL.class).deleteByKey(item.getKey())).
                            collect(Collectors.toList());
                    list.stream().forEach(System.out::println);
                } catch (NullPointerException ignored) {
                }
            }
        });
        //daemon.setDaemon(true);
        daemon.start();

    }
}