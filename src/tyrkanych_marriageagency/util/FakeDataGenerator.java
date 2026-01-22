package tyrkanych_marriageagency.util;

import net.datafaker.Faker;
import tyrkanych_marriageagency.model.Client;
import tyrkanych_marriageagency.model.Gender;
import tyrkanych_marriageagency.model.Interest;
import tyrkanych_marriageagency.model.Profile;

public class FakeDataGenerator {

    private static final Faker faker = new Faker();

    public static Client generateClient() {

        // Створюємо профіль з випадковим Gender
        Profile profile = new Profile(
              faker.number().numberBetween(18, 60),
              faker.address().city(),
              faker.bool().bool() ? Gender.MALE : Gender.FEMALE
        );

        profile.getInterests().add(new Interest("Music"));
        profile.getInterests().add(new Interest("Travel"));

        return new Client(
              faker.internet().emailAddress(),
              faker.internet().password(8, 12),
              profile
        );
    }
}
