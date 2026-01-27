package tyrkanych_marriageagency.unitofwork;

import tyrkanych_marriageagency.repository.impl.AdminRepositoryImpl;
import tyrkanych_marriageagency.repository.impl.ClientRepositoryImpl;

public class UnitOfWork {

    private final ClientRepositoryImpl clients = new ClientRepositoryImpl();
    private final AdminRepositoryImpl admins = new AdminRepositoryImpl();

    public ClientRepositoryImpl clients() {
        return clients;
    }

    public AdminRepositoryImpl admins() {
        return admins;
    }

    public void commit() {
        clients.commit();
    }
}
