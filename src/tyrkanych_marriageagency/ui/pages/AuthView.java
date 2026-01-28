package tyrkanych_marriageagency.ui.pages;

import java.util.Scanner;
import tyrkanych_marriageagency.dto.UserRegisterDto;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.model.User;
import tyrkanych_marriageagency.repository.AdminRepository;
import tyrkanych_marriageagency.service.AuthService;
import tyrkanych_marriageagency.service.ProfileService;
import tyrkanych_marriageagency.service.UserService;
import tyrkanych_marriageagency.ui.ascii.AsciiArt;
import tyrkanych_marriageagency.ui.forms.ProfileForm;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;
import tyrkanych_marriageagency.util.ConsoleColors;

public class AuthView {

    private final UserService userService;
    private final AuthService authService;
    private final UnitOfWork uow;
    private final Scanner sc = new Scanner(System.in);
    private final AdminRepository adminRepository;

    public AuthView(UserService userService, AuthService authService, UnitOfWork uow,
          AdminRepository adminRepository) {
        this.userService = userService;
        this.authService = authService;
        this.uow = uow;
        this.adminRepository = adminRepository;
    }

    public User show() {
        AsciiArt.printLogoWelcome();
        while (true) {
            System.out.println(
                  ConsoleColors.BLUE + "\n ШЛЮБНЕ АГЕНТСТВО " + ConsoleColors.RESET);
            System.out.println("1 - Вхід");
            System.out.println("2 - Реєстрація");
            System.out.println("0 - Вийти з програми");
            System.out.print("Ваш вибір: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    User user = login(); // повертає User
                    if (user != null) {
                        return user;
                    }
                }
                case 2 -> {
                    Client user = register(); // тут тільки клієнт
                    if (user != null) {
                        return user;
                    }
                }
                case 0 -> System.exit(0);
                default -> System.out.println("Невірний вибір");
            }
        }
    }

    private User login() {
        AsciiArt.printAuthorization();
        System.out.println("\n ВХІД ");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Пароль: ");
        String password = sc.nextLine();

        try {
            User user = authService.login(email, password);
            System.out.println(ConsoleColors.GREEN + " Вхід успішний!" + ConsoleColors.RESET);

            if (user instanceof Client client) {
                if (client.getProfile() == null) {
                    System.out.println(" Заповніть анкету, будь ласка");
                    if (!fillProfileWithAgeCheck(client)) {
                        return null;
                    }
                }
            }

            return user;
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + e.getMessage() + ConsoleColors.RESET);
            return null;
        }
    }

    private Client register() {
        AsciiArt.printRegistration();
        System.out.println("\n РЕЄСТРАЦІЯ ");

        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Пароль: ");
        String password = sc.nextLine();
        System.out.print("Підтвердіть пароль: ");
        String confirm = sc.nextLine();

        try {
            Client user = userService.register(
                  new UserRegisterDto(email, password, confirm)
            );

            System.out.println(ConsoleColors.GREEN + " Реєстрація успішна!" + ConsoleColors.RESET);
            System.out.println(" Заповніть анкету, будь ласка");

            if (!fillProfileWithAgeCheck(user)) {
                return null;
            }

            return user;
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + e.getMessage() + ConsoleColors.RESET);
            return null;
        }
    }

    private boolean fillProfileWithAgeCheck(Client user) {
        ProfileService profileService = new ProfileService(uow.clients());
        ProfileForm form = new ProfileForm(user, profileService);

        int age = form.askAge();
        if (age < 18) {
            System.out.println(ConsoleColors.RED +
                  " Вибачте, щоб користуватись програмою, вам має бути більше 18 років" +
                  ConsoleColors.RESET);

            System.out.println("1 - Повернутись у головне меню");
            System.out.println("0 - Вийти з програми");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 0) {
                System.exit(0);
            }
            return false;
        }

        form.fillProfile(age);
        uow.commit();
        return true;
    }
}
