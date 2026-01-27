package tyrkanych_marriageagency.ui.pages;

import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.util.ConsoleColors;

public class ProfileView {

    private final Client client;

    public ProfileView(Client client) {
        this.client = client;
    }

    public void show() {
        System.out.println(ConsoleColors.YELLOW +
              "\n=== АНКЕТА КОРИСТУВАЧА ===" +
              ConsoleColors.RESET);

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
}
