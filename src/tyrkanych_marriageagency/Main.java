package tyrkanych_marriageagency;

import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.service.MatcherService;
import tyrkanych_marriageagency.unitofwork.UnitOfWork;
import tyrkanych_marriageagency.util.FakeDataGenerator;

public class Main {

    static void main(String[] args) {

        UnitOfWork uow = new UnitOfWork();
        MatcherService matcher = new MatcherService();

        Client c1 = FakeDataGenerator.generateClient();
        Client c2 = FakeDataGenerator.generateClient();
        Client c3 = FakeDataGenerator.generateClient();

        uow.clients().create(c1);
        uow.clients().create(c2);
        uow.clients().create(c3);

        System.out.println("Всі клієнти:");
        uow.clients().findAll().forEach(c ->
              System.out.println(c.getEmail() + " | Місто: " + c.getProfile().getCity())
        );

        Client found = uow.clients().findByEmail(c1.getEmail());
        System.out.println("\nЗнайдено клієнта за email: " + found.getEmail());

        System.out.println("\nФільтрація за містом (" + c1.getProfile().getCity() + "):");
        uow.clients().filterByCity(c1.getProfile().getCity())
              .forEach(c -> System.out.println(c.getEmail()));

        System.out.println("\nВидаляємо клієнта: " + c2.getEmail());
        uow.clients().delete(c2.getId());

        uow.commit();
        System.out.println("\nCRUD виконано, дані збережено");

        System.out.println("\n--- DEMO: Лайки та матчі ---");
        matcher.like(c1, c3);
        matcher.like(c3, c1);

        System.out.println("\nУсі матчі:");
        matcher.getMatches().forEach(m ->
              System.out.println(m.getClientA().getEmail() + " ❤️ " + m.getClientB().getEmail())
        );
    }
}