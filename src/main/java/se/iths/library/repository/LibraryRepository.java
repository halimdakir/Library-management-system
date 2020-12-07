package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Library;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Long> {
}
