package tyrkanych_marriageagency.ui.pages;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.service.MatcherService;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;
import tyrkanych_marriageagency.util.ConsoleColors;

public class MainMenuView {

    private final Client currentUser;
    private final UnitOfWork uow;
    private final MatcherService matcherService;
    private final Scanner sc = new Scanner(System.in);

    public MainMenuView(Client currentUser, UnitOfWork uow, MatcherService matcherService) {
        this.currentUser = currentUser;
        this.uow = uow;
        this.matcherService = matcherService;
    }

    public void show() {
        boolean exit = false;
        while (!exit) {
            System.out.println(ConsoleColors.BLUE + "\n=== Головне меню ===" + ConsoleColors.RESET);
            System.out.println("1 - Дивитися анкети");
            System.out.println("2 - Подивитись свою анкету");
            System.out.println("3 - Хто мене лайкнув");
            System.out.println("4 - MATCH з людиною");
            System.out.println("5 - Повідомлення");
            System.out.println("6 - Вихід у стартове меню");
            System.out.println("0 - Вихід з програми");
            System.out.print("Ваш вибір: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> browseProfiles();
                case 2 -> new tyrkanych_marriageagency.ui.pages.UserView(currentUser, uow).show();
                case 3 -> showWhoLikedMe();
                case 4 -> showMatches();
                case 5 -> System.out.println(" Повідомлення поки що не реалізовані");
                case 6 -> exit = true;
                case 0 -> System.exit(0);
                default -> System.out.println(
                      ConsoleColors.RED + " Невірний вибір" + ConsoleColors.RESET);
            }
        }
    }

    private void browseProfiles() {
        List<Client> clients = uow.clients().findAll();
        Collections.shuffle(clients);

        for (Client c : clients) {
            if (c.getId().equals(currentUser.getId())) {
                continue;
            }

            new tyrkanych_marriageagency.ui.pages.UserView(c, uow).show();
            System.out.println("1 - Лайк ❤  2 - Далі  3 - Назад  4 - Поскаржитись ");
            System.out.print("Ваш вибір: ");
            int action = Integer.parseInt(sc.nextLine());

            switch (action) {
                case 1 -> matcherService.like(currentUser, c);
                case 2 -> {
                    continue;
                }
                case 3 -> {
                    return;
                }
                case 4 -> System.out.println(" Скарга на користувача надіслана");
                default -> System.out.println(
                      ConsoleColors.RED + " Невірний вибір" + ConsoleColors.RESET);
            }
        }
    }

    private void showWhoLikedMe() {
        System.out.println("\n Користувачі, які вас лайкнули:");
        uow.clients().findAll().stream()
              .filter(c -> c.getProfile() != null && c.getProfile().hasLiked(currentUser))
              .forEach(c -> System.out.println(
                    c.getProfile().getFirstName() + " " + c.getProfile().getLastName()));
    }

    private void showMatches() {
        System.out.println("\n Ваші MATCHES:");
        matcherService.getMatches().stream()
              .filter(m -> m.getClientA() == currentUser || m.getClientB() == currentUser)
              .forEach(m -> {
                  Client other = m.getClientA() == currentUser ? m.getClientB() : m.getClientA();
                  System.out.println(
                        other.getProfile().getFirstName() + " " + other.getProfile().getLastName());
              });
    }
}
