package tyrkanych_marriageagency.ui.pages;

import java.util.Scanner;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.service.ProfileService;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;
import tyrkanych_marriageagency.util.ConsoleColors;

public class UserView {

    private final Client client;
    private final UnitOfWork uow;
    private final Scanner sc = new Scanner(System.in);

    public UserView(Client client, UnitOfWork uow) {
        this.client = client;
        this.uow = uow;
    }

    public void show() {
        while (true) {
            System.out.println(ConsoleColors.YELLOW + "\n=== МОЯ АНКЕТА ===" + ConsoleColors.RESET);

            printProfile();

            System.out.println("\n1 - ✏ Змінити анкету");
            System.out.println("0 -  Назад у меню");
            System.out.print("Ваш вибір: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> editProfile();
                case 0 -> {
                    return;
                }
                default -> System.out.println(
                      ConsoleColors.RED + "Невірний вибір" + ConsoleColors.RESET);
            }
        }
    }

    private void printProfile() {
        if (client.getProfile() == null) {
            System.out.println("Анкета не заповнена");
            return;
        }

        System.out.println("Ім'я: " + client.getProfile().getFirstName());
        System.out.println("Прізвище: " + client.getProfile().getLastName());
        System.out.println("Вік: " + client.getProfile().getAge());
        System.out.println("Місто: " + client.getProfile().getCity());
        System.out.println("Опис: " + client.getProfile().getDescription());
    }

    private void editProfile() {
        ProfileService profileService = new ProfileService(uow.clients());
        int age;
        try {
            System.out.print("Вік: ");
            age = Integer.parseInt(sc.nextLine());
            if (age < 18) {
                System.out.println(
                      ConsoleColors.RED + "Вік має бути не менше 18 років" + ConsoleColors.RESET);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Невірне число" + ConsoleColors.RESET);
            return;
        }

        System.out.print("Ім'я: ");
        String firstName = sc.nextLine();

        System.out.print("Прізвище: ");
        String lastName = sc.nextLine();

        System.out.print("Місто: ");
        String city = sc.nextLine();

        System.out.print("Опис: ");
        String description = sc.nextLine();

        profileService.createOrUpdateProfile(
              client,
              age,
              city,
              description,
              client.getProfile() != null ? client.getProfile().getInterests() : null
        );

        client.getProfile().setFirstName(firstName);
        client.getProfile().setLastName(lastName);

        uow.commit();
        System.out.println(
              ConsoleColors.GREEN + " Анкета успішно оновлена!" + ConsoleColors.RESET);
    }
}
