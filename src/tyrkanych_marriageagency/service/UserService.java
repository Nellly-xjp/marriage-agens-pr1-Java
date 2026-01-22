package tyrkanych_marriageagency.service;

import tyrkanych_marriageagency.dto.UserRegisterDto;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.repository.ClientRepository;
import tyrkanych_marriageagency.validation.UserValidator;

public class UserService {

    private final ClientRepository clientRepository;

    public UserService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client register(UserRegisterDto dto) {
        UserValidator.validateEmail(dto.email());
        UserValidator.validatePassword(dto.password());

        if (!dto.password().equals(dto.confirmPassword())) {
            throw new IllegalArgumentException("Паролі не співпадають");
        }

        if (clientRepository.findByEmail(dto.email()) != null) {
            throw new IllegalStateException("Користувач з таким email вже існує");
        }

        Client client = new Client(dto.email(), dto.password(), null); // профіль null
        clientRepository.create(client);
        return client;
    }
}
