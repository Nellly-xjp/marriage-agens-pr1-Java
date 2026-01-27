package tyrkanych_marriageagency.repository.impl;

import java.util.HashMap;
import java.util.Map;
import tyrkanych_marriageagency.model.Admin;
import tyrkanych_marriageagency.repository.AdminRepository;
import tyrkanych_marriageagency.util.PasswordUtil;

public class AdminRepositoryImpl implements AdminRepository {

    private final Map<String, Admin> admins = new HashMap<>();

    public AdminRepositoryImpl() {
        admins.put(
              "admin@mail.com",
              new Admin(
                    "admin@mail.com",
                    PasswordUtil.hash("admin123")
              )
        );
    }

    @Override
    public Admin findByEmail(String email) {
        return admins.get(email);
    }
}
