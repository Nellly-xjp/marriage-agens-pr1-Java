package tyrkanych_marriageagency.model;

import java.util.UUID;

public abstract class User {

    protected final String id;
    protected String email;
    protected String password;

    protected User(String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
    }

    public abstract String getRole();

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
