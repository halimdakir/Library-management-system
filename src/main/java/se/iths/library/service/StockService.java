package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Library;
import se.iths.library.entity.Stock;
import se.iths.library.repository.StockRepository;

import java.util.Optional;

@Service
public class StockService {
    @Autowired
    StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }
}
