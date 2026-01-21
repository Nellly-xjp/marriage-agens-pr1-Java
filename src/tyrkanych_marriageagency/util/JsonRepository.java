package tyrkanych_marriageagency.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class JsonRepository<T> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveToFile(String fileName, List<T> data) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("Помилка запису файлу", e);
        }
    }

    public List<T> loadFromFile(String fileName, Class<T> clazz) {
        File file = new File(fileName);
        if (!file.exists()) {
            return Collections.emptyList();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
