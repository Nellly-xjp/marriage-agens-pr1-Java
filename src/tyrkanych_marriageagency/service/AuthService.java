package tyrkanych_marriageagency.service;

import tyrkanych_marriageagency.model.User;
import tyrkanych_marriageagency.repository.ClientRepository;
import tyrkanych_marriageagency.util.PasswordUtil;
import tyrkanych_marriageagency.validation.UserValidator;

public class AuthService {

    private final ClientRepository clientRepository;

    public AuthService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public User login(String email, String password) {

        UserValidator.validateEmail(email);
        UserValidator.validatePassword(password);

        User user = clientRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Користувача не знайдено");
        }

        // 🔐 хешуємо введений пароль і порівнюємо з хешем у базі
        String hashedInput = PasswordUtil.hash(password);
        if (!user.getPassword().equals(hashedInput)) {
            throw new IllegalArgumentException("Невірний пароль");
        }

        return user;
    }
}
