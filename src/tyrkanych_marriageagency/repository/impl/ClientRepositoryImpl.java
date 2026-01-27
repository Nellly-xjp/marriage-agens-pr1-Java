package tyrkanych_marriageagency.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.repository.ClientRepository;
import tyrkanych_marriageagency.util.JsonRepository;

public class ClientRepositoryImpl implements ClientRepository {

    private final Map<String, Client> identityMap = new HashMap<>();
    private final JsonRepository<Client> jsonRepo = new JsonRepository<>();
    private final String file = "clients.json";

    public ClientRepositoryImpl() {
        load();
    }

    @Override
    public void create(Client client) {
        identityMap.put(client.getId(), client);
    }

    @Override
    public Optional<Client> findById(String id) {
        return Optional.ofNullable(identityMap.get(id));
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(identityMap.values());
    }

    @Override
    public void update(Client client) {
        identityMap.put(client.getId(), client);
    }

    @Override
    public void delete(String id) {
        identityMap.remove(id);
    }

    @Override
    public Client findByEmail(String email) {
        return identityMap.values().stream()
              .filter(c -> c.getEmail().equals(email))
              .findFirst()
              .orElse(null);
    }

    @Override
    public List<Client> filterByCity(String city) {
        return identityMap.values().stream()
              .filter(c -> c.getProfile().getCity().equalsIgnoreCase(city))
              .toList();
    }

    public void commit() {
        jsonRepo.saveToFile(file, findAll());
    }

    private void load() {
        List<Client> clientsFromFile = jsonRepo.loadFromFile(file, Client.class);
        if (clientsFromFile != null) {
            clientsFromFile.forEach(c -> identityMap.put(c.getId(), c));
        }
    }
}