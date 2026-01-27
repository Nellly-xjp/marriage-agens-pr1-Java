package tyrkanych_marriageagency.ui.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.model.Message;
import tyrkanych_marriageagency.service.MatcherService;
import tyrkanych_marriageagency.service.MessageService;
import tyrkanych_marriageagency.ui.forms.MessageForm;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;

public class MainMenuView {

    private final Client currentUser;
    private final UnitOfWork uow;
    private final MatcherService matcherService;
    private final MessageService messageService;

    private final Scanner sc = new Scanner(System.in);
    private final MessageForm messageForm = new MessageForm();

    public MainMenuView(
          Client currentUser,
          UnitOfWork uow,
          MatcherService matcherService,
          MessageService messageService
    ) {
        this.currentUser = currentUser;
        this.uow = uow;
        this.matcherService = matcherService;
        this.messageService = messageService;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== Головне меню ===");
            System.out.println("1 - Дивитися анкети");
            System.out.println("2 - Моя анкета");
            System.out.println("3 - Хто мене лайкнув");
            System.out.println("4 - MATCH");
            System.out.println("5 - Повідомлення");
            System.out.println("6 - Вийти з акаунту");
            System.out.println("0 - Вийти");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> browseProfiles();
                case 2 -> new UserView(currentUser, uow).show();
                case 3 -> showWhoLikedMe();
                case 4 -> showMatches();
                case 5 -> showMessages();
                case 6 -> {
                    return;
                }
                case 0 -> System.exit(0);
            }
        }
    }

    private void browseProfiles() {
        List<Client> clients = new ArrayList<>(filterProfilesForUser());
        Collections.shuffle(clients);

        if (clients.isEmpty()) {
            System.out.println("Немає підходящих анкет");
            return;
        }

        for (Client c : clients) {
            new ProfileView(c).show();

            System.out.println("1 - ❤ Лайк   2 - Далі   0 - Назад");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                matcherService.like(currentUser, c);
            } else if (choice == 0) {
                return;
            }
        }
    }


    private void showWhoLikedMe() {
        List<Client> likedMe = uow.clients().findAll().stream()
              .filter(c ->
                    c.getProfile() != null &&
                          c.getProfile().hasLiked(currentUser) &&
                          !currentUser.getProfile().hasLiked(c)
              )
              .toList();

        if (likedMe.isEmpty()) {
            System.out.println("Вас ще ніхто не лайкнув");
            return;
        }

        for (Client c : likedMe) {
            System.out.println("\n❤ Вас лайкнув/ла:");
            new ProfileView(c).show();

            System.out.println("1 - ❤ Лайкнути у відповідь");
            System.out.println("0 - Назад");

            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                matcherService.like(currentUser, c);
            } else {
                return;
            }
        }
    }

    private void showMatches() {
        boolean found = false;

        for (Client c : uow.clients().findAll()) {
            if (matcherService.isMatch(currentUser, c)) {
                found = true;
                new ProfileView(c).show();

                System.out.println("1 - ✉ Написати");
                System.out.println("0 - Назад");

                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1) {
                    String text = messageForm.show(
                          c.getProfile().getFirstName(),
                          c.getProfile().getLastName()
                    );

                    messageService.send(
                          currentUser.getId(),
                          c.getId(),
                          text
                    );

                    System.out.println("✅ Повідомлення надіслано");
                }
            }
        }

        if (!found) {
            System.out.println("💔 У вас ще немає MATCH");
        }
    }

    private void showMessages() {
        List<Message> inbox = messageService.inbox(currentUser.getId());

        if (inbox.isEmpty()) {
            System.out.println("📭 Немає повідомлень");
            return;
        }

        for (Message m : inbox) {
            Optional<Client> fromOpt = uow.clients().findById(m.fromUserId());
            if (fromOpt.isPresent()) {
                Client from = fromOpt.get();
                System.out.println("💬 Від " +
                      from.getProfile().getFirstName() +
                      ": " + m.text());
            }
        }
    }

    private List<Client> filterProfilesForUser() {
        int age = currentUser.getProfile().getAge();
        String city = currentUser.getProfile().getCity();

        return uow.clients().findAll().stream()
              .filter(c -> !c.getId().equals(currentUser.getId()))
              .filter(c -> c.getProfile() != null)
              .filter(c -> c.getProfile().getCity().equalsIgnoreCase(city))
              .filter(c -> Math.abs(c.getProfile().getAge() - age) <= 4)
              .toList();
    }
}
