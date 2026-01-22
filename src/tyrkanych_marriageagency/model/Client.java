package tyrkanych_marriageagency.model;

public class Client extends User {


    private Profile profile;

    public Client(String email, String password, Profile profile) {
        super(email, password);
        this.profile = profile;
    }


    @Override
    public String getRole() {
        return "CLIENT";
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
