package tyrkanych_marriageagency.service;

import tyrkanych_marriageagency.dto.UserRegisterDto;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.repository.ClientRepository;
import tyrkanych_marriageagency.util.PasswordUtil;
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

        // 🔐 хешуємо пароль перед збереженням
        String hashedPassword = PasswordUtil.hash(dto.password());

        Client client = new Client(dto.email(), hashedPassword, null);
        clientRepository.create(client);
        return client;
    }
}
