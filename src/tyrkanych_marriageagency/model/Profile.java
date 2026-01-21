package tyrkanych_marriageagency.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Profile {

    private final int age;
    private final String city;
    private final Gender gender;
    private final List<Interest> interests = new ArrayList<>();
    private final Set<String> likedClients = new HashSet<>();

    public Profile(int age, String city, Gender gender) {
        this.age = age;
        this.city = city;
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public Gender getGender() {
        return gender;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    // лайки
    public void like(Client client) {
        likedClients.add(client.getId());
    }

    public boolean hasLiked(Client client) {
        return likedClients.contains(client.getId());
    }
}
