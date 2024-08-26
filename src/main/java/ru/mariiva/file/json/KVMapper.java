package ru.mariiva.file.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.mariiva.entities.KV;
import ru.mariiva.entities.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для конвертации данных в json  и обратно
 */
public class KVMapper {
    /**
     * Извлекает список объектов из json массива
     * @param jsonArray - json массив
     * @return - список объектов
     */
    public static List<KV> fromJSONArray(JSONArray jsonArray){
        List<KV> list = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            list.add(fromJSON(jsonArray.getJSONObject(i)));
        }
        return list;
    }

    /**
     *
     * @param jsonObject - json объект
     * @return - объект класса
     */
    private static KV fromJSON(JSONObject jsonObject) {
        KV kv = null;
        try {
            kv = KV.builder().
                    key(jsonObject.getString("key")).
                    value(
                            Value.builder().
                                    data(jsonObject.getJSONObject("value").getString("data")).
                                    ttl(jsonObject.getJSONObject("value").getInt("ttl")).
                                    build()
                    ).
                    build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return kv;
    }
}
