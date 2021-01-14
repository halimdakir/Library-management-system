package se.iths.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.iths.library.dto.StockDTO;
import se.iths.library.entity.Stock;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

    @Query("SELECT new se.iths.library.dto.StockDTO( s.id, s.quantity, i.barCode, i.title) FROM Stock s INNER JOIN s.item i WHERE i.title like %:title%")
    List<StockDTO> findItemStockByTitle(String title);

    @Query("SELECT new se.iths.library.dto.StockDTO( s.id, s.quantity, i.barCode, i.title) FROM Stock s INNER JOIN s.item i")
    List<StockDTO> findAllItemStocks();

    @Query("SELECT new se.iths.library.dto.StockDTO( s.id, s.quantity, i.barCode, i.title) FROM Stock s INNER JOIN s.item i INNER JOIN i.authors a WHERE a.fullName LIKE %:fullName%")
    List<StockDTO> findItemStockByAuthorName(String fullName);

    @Query("SELECT DISTINCT s FROM Stock s INNER JOIN FETCH s.item i WHERE i.id=:id")
    Stock findStockByItemId(@Param("id") Long id);
}
