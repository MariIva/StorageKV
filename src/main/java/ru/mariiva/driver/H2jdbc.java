package ru.mariiva.driver;

import ru.mariiva.rest.dto.KV_DTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2jdbc {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";
    //url=jdbc:h2:mem:testdb user=SA

    Connection conn;
    public H2jdbc() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER, PASS);

        System.out.println(conn.getMetaData().getURL());
        //System.out.println(conn.);
//        conn.close();
    }

    public void close() throws SQLException {
        conn.close();
    }

    public List<KV_DTO> getAll() throws SQLException {
        List<KV_DTO> list = new ArrayList<>();

        Statement stmt = conn.createStatement();
        String sql = "select * from kv";

        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("data");
            list.add(KV_DTO.builder().
                    key(id).
                    value(name).
                    build());
        }
        return list;
    }
}
