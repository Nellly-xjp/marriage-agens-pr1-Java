package tyrkanych_marriageagency.ui;

import java.util.Scanner;
import tyrkanych_marriageagency.dto.UserRegisterDto;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.service.AuthService;
import tyrkanych_marriageagency.service.MatcherService;
import tyrkanych_marriageagency.service.ProfileService;
import tyrkanych_marriageagency.service.UserService;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;

public class ConsoleApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void start() {
        UnitOfWork uow = new UnitOfWork();
        UserService userService = new UserService(uow.clients());
        AuthService authService = new AuthService(uow.clients());
        ProfileService profileService = new ProfileService(uow.clients());
        MatcherService matcherService = new MatcherService();

        while (true) {
            System.out.println("\n=== ШЛЮБНЕ АГЕНТСТВО ===");
            System.out.println("1 - Реєстрація");
            System.out.println("2 - Вхід");
            System.out.println("0 - Вихід");
            System.out.print("Ваш вибір: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> register(userService, uow);
                case 2 -> loginMenu(authService, profileService, matcherService, uow);
                case 0 -> {
                    System.out.println("До побачення!");
                    return;
                }
                default -> System.out.println("Невірний вибір");
            }
        }
    }

    private static void register(UserService userService, UnitOfWork uow) {
        try {
            String email = readString("Email: ");
            String password = readString("Пароль: ");
            String confirm = readString("Підтвердіть пароль: ");
            UserRegisterDto dto = new UserRegisterDto(email, password, confirm);

            userService.register(dto);
            uow.commit();
            System.out.println("✅ Реєстрація успішна!");
        } catch (Exception e) {
            System.out.println("❌ Помилка: " + e.getMessage());
        }
    }

    private static void loginMenu(AuthService authService,
          ProfileService profileService,
          MatcherService matcherService,
          UnitOfWork uow) {
        try {
            String email = readString("Email: ");
            String password = readString("Пароль: ");
            Client client = (Client) authService.login(email, password);
            System.out.println("✅ Вхід успішний!");

            boolean exit = false;
            while (!exit) {
                System.out.println("\n--- Меню ---");
                System.out.println("1 - Переглянути профілі");
                System.out.println("2 - Лайкнути клієнта");
                System.out.println("3 - Переглянути матчі");
                System.out.println("0 - Вихід");
                int choice = readInt();

                switch (choice) {
                    case 1 -> uow.clients().findAll().forEach(c -> {
                        if (!c.getId().equals(client.getId())) {
                            System.out.println(c.getEmail() + " | " +
                                  (c.getProfile() != null ? c.getProfile().getCity()
                                        : "Немає профілю"));
                        }
                    });
                    case 2 -> {
                        String targetEmail = readString("Email клієнта для лайку: ");
                        Client target = uow.clients().findByEmail(targetEmail);
                        if (target != null) {
                            matcherService.like(client, target);
                            System.out.println("Лайк відправлено!");
                        } else {
                            System.out.println("Клієнта не знайдено");
                        }
                    }
                    case 3 -> matcherService.getMatches().forEach(m ->
                          System.out.println(
                                m.getClientA().getEmail() + " ❤️ " + m.getClientB().getEmail()));
                    case 0 -> exit = true;
                    default -> System.out.println("Невірний вибір");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Помилка: " + e.getMessage());
        }
    }

    private static String readString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    private static int readInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}
