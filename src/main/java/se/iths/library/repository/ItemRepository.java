package se.iths.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Item;


@Repository
public interface ItemRepository extends CrudRepository <Item,Long> {

    @Query("SELECT i FROM Item i where i.title like %:title%")
    Iterable<Item> findItemByTitle(@Param("title") String title);

    @Query("SELECT DISTINCT i FROM Item i INNER JOIN FETCH i.authors a WHERE a.fullName LIKE %:fullName%")
    Iterable<Item> findItemByAuthorName(@Param("fullName") String fullName);

    Item findItemByBarCode(String barCode);

    Item findItemById(Long id);

}
