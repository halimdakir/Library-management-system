package se.iths.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.library.entity.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
}
