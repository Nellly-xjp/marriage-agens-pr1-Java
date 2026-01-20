package tyrkanych_marriageagency.model;

public class Admin extends User {

    public Admin(String email, String password) {
        super(email, password);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}