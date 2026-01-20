package tyrkanych_marriageagency.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
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
}
