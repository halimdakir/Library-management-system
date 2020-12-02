package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Librarian;

@Repository
public interface LibrarianRepository extends CrudRepository<Librarian, Long> {
}
