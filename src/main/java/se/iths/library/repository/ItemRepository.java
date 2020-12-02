package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository <Item,Long> {
}
