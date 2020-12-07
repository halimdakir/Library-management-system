package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
