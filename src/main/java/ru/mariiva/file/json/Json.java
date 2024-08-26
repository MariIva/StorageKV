package ru.mariiva.file.json;

import org.json.JSONArray;
import org.json.JSONTokener;
import ru.mariiva.entities.KV;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Класс, работающий с json файлом
 * PATH_JSON - путь к файлу
 */
public class Json {
    public static final String PATH_JSON = "src/main/resources/db.changelog/data/json/db_data.json";

    /**
     * Полчает данные из файла в json формате
     * @return - json массив
     */
    public static JSONArray getJsonFromResource() {
        try(InputStream is = Files.newInputStream(Path.of(PATH_JSON))) {
            JSONTokener tokener = new JSONTokener(is);
            return new JSONArray(tokener);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Конвертирует список объектов в json массив и сохраняет в файле
     * @param list - список объектов
     */
    public static void toJsonFile( List<KV> list){
        JSONArray jsonArray = new JSONArray(list);
        String myJson = jsonArray.toString();
        try(PrintWriter pw = new PrintWriter(PATH_JSON)) {
            pw.print(myJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
