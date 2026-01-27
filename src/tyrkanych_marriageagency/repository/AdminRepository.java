package tyrkanych_marriageagency.repository;

import tyrkanych_marriageagency.model.Admin;
import tyrkanych_marriageagency.repository.impl.AdminRepositoryImpl;

public interface AdminRepository {

    AdminRepository adminRepository = new AdminRepositoryImpl();

    Admin findByEmail(String email);
}