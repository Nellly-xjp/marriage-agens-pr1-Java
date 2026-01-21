package tyrkanych_marriageagency.repository;

import java.util.List;
import tyrkanych_marriageagency.model.Client;

public interface ClientRepository extends CrudRepository<Client> {

    Client findByEmail(String email);

    List<Client> filterByCity(String city);
}
