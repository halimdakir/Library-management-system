package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.ItemLending;

@Repository
public interface ItemLendingRepository extends CrudRepository<ItemLending, Long> {
}
