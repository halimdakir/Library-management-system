package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.ItemLending;

@Repository
public interface ItemLendingRepository extends CrudRepository<ItemLending, Long> {
    //@Query("SELECT DISTINCT i.barCode, i.title, it.creationDate, it.dueDate FROM ItemLending it INNER JOIN FETCH it.item i WHERE i.title =:title")

    //@Query("SELECT it, i FROM ItemLending it inner join it.item i inner join it.login l inner join l.user u where u.id =:id")
    //List<Object[]> findItemLendingsByLogin_User(@Param("id") Long id);
}
