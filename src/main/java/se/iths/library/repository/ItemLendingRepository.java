package se.iths.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.ItemLending;
import se.iths.library.dto.BorrowedItemsDTO;
import java.util.List;

@Repository
public interface ItemLendingRepository extends CrudRepository<ItemLending, Long> {
    @Query("SELECT new se.iths.library.dto.BorrowedItemsDTO( i.title, i.barCode, il.creationDate, il.dueDate) FROM ItemLending il INNER JOIN il.item i INNER JOIN il.user u INNER JOIN u.login l WHERE l.email=:email")
    List<BorrowedItemsDTO> findByBorrowedItemByUserEmail(String email);

    @Query("SELECT new se.iths.library.dto.BorrowedItemsDTO( i.title, i.barCode, il.creationDate, il.dueDate) FROM ItemLending il INNER JOIN il.item i INNER JOIN il.user u WHERE u.id=:id")
    List<BorrowedItemsDTO> findByBorrowedItemByUserId(Long id);

}
