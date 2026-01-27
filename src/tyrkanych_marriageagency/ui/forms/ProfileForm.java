package tyrkanych_marriageagency.ui.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.model.Interest;
import tyrkanych_marriageagency.service.ProfileService;

public class ProfileForm {

    private final Client client;
    private final ProfileService profileService;
    private final Scanner sc = new Scanner(System.in);

    public ProfileForm(Client client, ProfileService profileService) {
        this.client = client;
        this.profileService = profileService;
    }

    public int askAge() {
        System.out.print("Вік: ");
        return Integer.parseInt(sc.nextLine());
    }

    public void fillProfile(int age) {

        System.out.print("Ім'я: ");
        String firstName = sc.nextLine();

        System.out.print("Прізвище: ");
        String lastName = sc.nextLine();

        System.out.print("Місто: ");
        String city = sc.nextLine();

        System.out.print("Опис: ");
        String description = sc.nextLine();

        List<Interest> interests = new ArrayList<>();

        profileService.createOrUpdateProfile(
              client,
              age,
              city,
              description,
              interests
        );

        client.getProfile().setFirstName(firstName);
        client.getProfile().setLastName(lastName);
    }
}
