package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.dto.StockDTO;
import se.iths.library.entity.Stock;
import se.iths.library.exception.NotFoundException;
import se.iths.library.repository.StockRepository;

import java.util.List;

@Service
public class StockService {
    @Autowired
    StockRepository stockRepository;


    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }

    public Stock updateStockQuantity(int newQuantity, Long id){
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setQuantity(newQuantity);
                    return stockRepository.save(stock);
                })
                .orElseThrow(() -> new NotFoundException("Stock not found with id :" + id)
                );
    }
    public List<StockDTO> getItemStockByTitle(String title){
        return stockRepository.findItemStockByTitle(title);
    }
    public List<StockDTO> getAllItemStocks(){
        return stockRepository.findAllItemStocks();
    }
    public List<StockDTO> findItemStockByAuthorName(String fullName){
        return stockRepository.findItemStockByAuthorName(fullName);
    }
    public Stock getStockByItemId(Long id){
        return stockRepository.findStockByItemId(id);
    }
}
