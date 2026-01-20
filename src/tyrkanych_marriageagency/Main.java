package tyrkanych_marriageagency;

import java.util.ArrayList;
import java.util.List;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.util.FakeDataGenerator;
import tyrkanych_marriageagency.util.JsonRepository;

public class Main {

    static void main(String[] args) {

        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            clients.add(FakeDataGenerator.generateClient());
        }

        JsonRepository<Client> repository = new JsonRepository<>();
        repository.saveToFile("clients.json", clients);

        System.out.println(" Дані збережено у clients.json");
    }
}
