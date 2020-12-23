package se.iths.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.ItemLending;
import se.iths.library.models.BorrowedItemsDTO;
import java.util.List;

@Repository
public interface ItemLendingRepository extends CrudRepository<ItemLending, Long> {
    @Query("SELECT new se.iths.library.models.BorrowedItemsDTO( i.title, i.barCode, il.creationDate, il.dueDate) FROM ItemLending il INNER JOIN il.item i INNER JOIN il.user u WHERE u.id=:id")
    List<BorrowedItemsDTO> findByItem_Id(Long id);

}
