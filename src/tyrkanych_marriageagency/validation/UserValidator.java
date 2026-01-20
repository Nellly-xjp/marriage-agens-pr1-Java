package tyrkanych_marriageagency.validation;

public class UserValidator {

    public static void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new ValidationException("Некоректний email");
        }
    }

    public static void validatePassword(String password) {
        if (password.length() < 6) {
            throw new ValidationException("Пароль занадто короткий");
        }
    }
}