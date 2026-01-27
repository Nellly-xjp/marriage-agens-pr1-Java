package tyrkanych_marriageagency.ui.pages;

import java.util.List;
import java.util.Scanner;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.service.MatcherService;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;
import tyrkanych_marriageagency.util.ConsoleColors;

public class AdminMenuView {

    private final UnitOfWork uow;
    private final MatcherService matcherService;
    private final Scanner sc = new Scanner(System.in);

    public AdminMenuView(UnitOfWork uow, MatcherService matcherService) {
        this.uow = uow;
        this.matcherService = matcherService;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== Адмін-меню ===");
            System.out.println("1 - Список MATCH-ів");
            System.out.println("2 - Список всіх анкет");
            System.out.println("3 - Анкети зі скаргами");
            System.out.println("0 - Вийти");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> showMatches();
                case 2 -> showAllProfiles();
                case 3 -> showComplaints(); // поки заглушка
                case 0 -> {
                    System.out.println("Вихід з адмін-меню...");
                    return;
                }
                default -> System.out.println(
                      ConsoleColors.RED + "Невірний вибір" + ConsoleColors.RESET);
            }
        }
    }

    private void showMatches() {
        System.out.println("\n--- Список всіх MATCH-ів ---");
        List<Client> clients = uow.clients().findAll();

        boolean found = false;
        for (Client a : clients) {
            for (Client b : clients) {
                if (!a.getId().equals(b.getId()) && matcherService.isMatch(a, b)) {
                    found = true;
                    System.out.println(
                          "💖 " + a.getProfile().getFirstName() + " " + a.getProfile().getLastName()
                                +
                                " ↔ " + b.getProfile().getFirstName() + " " + b.getProfile()
                                .getLastName());
                }
            }
        }

        if (!found) {
            System.out.println("Немає MATCH-ів");
        }
    }

    private void showAllProfiles() {
        System.out.println("\n--- Список всіх анкет ---");
        List<Client> clients = uow.clients().findAll();
        if (clients.isEmpty()) {
            System.out.println("Анкет немає");
            return;
        }

        for (Client c : clients) {
            if (c.getProfile() != null) {
                System.out.println(
                      c.getProfile().getFirstName() + " " + c.getProfile().getLastName() +
                            " | Вік: " + c.getProfile().getAge() +
                            " | Місто: " + c.getProfile().getCity());
            }
        }
    }

    private void showComplaints() {
        System.out.println("\n--- Анкети зі скаргами ---");
        // реалізувати логіку скарг
        System.out.println("Поки що немає реалізації скарг");
    }
}
