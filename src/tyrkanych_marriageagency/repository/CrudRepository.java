package tyrkanych_marriageagency.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    void create(T entity);

    Optional<T> findById(String id);

    List<T> findAll();

    void update(T entity);

    void delete(String id);
}
