package tyrkanych_marriageagency.model;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    private final int age;
    private final String city;
    private final Gender gender;
    private final List<Interest> interests = new ArrayList<>();

    public Profile(int age, String city, Gender gender) {
        this.age = age;
        this.city = city;
        this.gender = gender;
    }

    public List<Interest> getInterests() {
        return interests;
    }
}