package se.iths.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Author;


@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Query("SELECT DISTINCT a FROM Author a INNER JOIN FETCH a.items i WHERE i.id=:id")
    Iterable<Author> findAuthorByItems_Id(@Param("id") Long id);

}
