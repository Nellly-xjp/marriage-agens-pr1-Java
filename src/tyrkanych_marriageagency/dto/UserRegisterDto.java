package tyrkanych_marriageagency.dto;

import tyrkanych_marriageagency.validation.UserValidator;

public record UserRegisterDto(
      String email,
      String password,
      String confirmPassword
) {

    public UserRegisterDto {
        UserValidator.validateEmail(email);
        UserValidator.validatePassword(password);

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Паролі не співпадають");
        }
    }
}
