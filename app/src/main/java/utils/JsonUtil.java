package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

public class JsonUtil {
    private static Gson gson = new GsonBuilder().create();

    public static <T> T parseJson(String jsonString, Class<T> type) {
        return gson.fromJson(jsonString, type);
    }

    public static <T> T parseJson(String jsonString, Type type) {
        return gson.fromJson(jsonString, type);
    }

    public static <T> T parseJson(Reader reader, Type type) {
        return gson.fromJson(reader, type);
    }


    public static String toJson(Object object) {
        return gson.toJson(object);
    }


}
