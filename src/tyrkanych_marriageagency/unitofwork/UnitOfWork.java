package tyrkanych_marriageagency.unitofwork;

import tyrkanych_marriageagency.repository.impl.ClientRepositoryImpl;

public class UnitOfWork {

    private final ClientRepositoryImpl clients = new ClientRepositoryImpl();

    public ClientRepositoryImpl clients() {
        return clients;
    }

    public void commit() {
        clients.commit();
    }
}
