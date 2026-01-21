package tyrkanych_marriageagency.model;

public class Client extends User {

    private final Profile profile;

    public Client(String email, String password, Profile profile) {
        super(email, password);
        if (profile == null) {
            throw new IllegalArgumentException("Profile cannot be null");
        }
        this.profile = profile;
    }

    @Override
    public String getRole() {
        return "CLIENT";
    }

    public Profile getProfile() {
        return profile;
    }
}
