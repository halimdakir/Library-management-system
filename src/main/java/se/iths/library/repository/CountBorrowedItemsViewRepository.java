package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.CountBorrowedItemsView;

@Repository
public interface CountBorrowedItemsViewRepository extends CrudRepository<CountBorrowedItemsView, Long> {
}
