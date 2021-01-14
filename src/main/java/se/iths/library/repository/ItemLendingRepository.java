package se.iths.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.iths.library.dto.BorrowedItemsDTO;
import se.iths.library.dto.ReservedItemDTO;
import se.iths.library.entity.ItemLending;
import java.util.List;

@Repository
public interface ItemLendingRepository extends CrudRepository<ItemLending, Long> {

    @Query("SELECT new se.iths.library.dto.BorrowedItemsDTO( il.id, i.title, i.barCode, il.creationDate, il.dueDate) FROM ItemLending il INNER JOIN il.item i INNER JOIN il.user u INNER JOIN u.login l WHERE l.email=:email AND il.isConfirmed = TRUE AND il.isReturned = FALSE")
    List<BorrowedItemsDTO> findByBorrowedItemByUserEmail(String email);

    @Query("SELECT new se.iths.library.dto.BorrowedItemsDTO( i.title, i.barCode, il.creationDate, il.dueDate) FROM ItemLending il INNER JOIN il.item i INNER JOIN il.user u WHERE u.id=:id AND il.isConfirmed = TRUE AND il.isReturned = FALSE")
    List<BorrowedItemsDTO> findByBorrowedItemByUserId(Long id);

    @Query("SELECT new se.iths.library.dto.BorrowedItemsDTO( il.id, i.title, i.barCode, il.creationDate, il.dueDate) FROM ItemLending il INNER JOIN il.item i INNER JOIN il.user u WHERE u.id=:id AND il.isConfirmed = FALSE")
    List<BorrowedItemsDTO> findByReservedItemByUserId(Long id);

    @Query("SELECT DISTINCT il FROM ItemLending il INNER JOIN FETCH il.item i WHERE i.barCode =:barCode")
    ItemLending findByItem_BarCode(@Param("barCode")String barCode);

    @Query("SELECT new se.iths.library.dto.ReservedItemDTO( il.id, i.barCode, i.title, u.fullName) FROM ItemLending il INNER JOIN il.item i INNER JOIN il.user u WHERE il.isConfirmed = FALSE")
    List<ReservedItemDTO> findAllReservedItemsByUserId();

    ItemLending findItemLendingById(Long id);

}
