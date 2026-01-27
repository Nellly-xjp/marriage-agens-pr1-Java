package tyrkanych_marriageagency.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Profile {

    private final List<Interest> interests = new ArrayList<>();
    private final Set<String> likedClients = new HashSet<>();
    private final Gender gender;
    private final List<String> complaints = new ArrayList<>();
    private int age;
    private String city;
    private String description = "";
    private String firstName;
    private String lastName;


    public Profile(int age, String city, Gender gender) {
        this.age = age;
        this.city = city;
        this.gender = gender != null ? gender : Gender.MALE; // дефолт MALE
    }

    public Profile(int age, String city) {
        this(age, city, Gender.MALE);
    }

    public void addComplaint(String text) {
        complaints.add(text);
    }

    public List<String> getComplaints() {
        return complaints;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Gender getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Interest> getInterests() {
        return interests;
    }


    public void like(Client client) {
        likedClients.add(client.getId());
    }

    public boolean hasLiked(Client client) {
        return likedClients.contains(client.getId());
    }
}

