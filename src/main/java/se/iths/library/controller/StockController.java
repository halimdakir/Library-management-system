package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.library.dto.StockDTO;
import se.iths.library.entity.Stock;
import se.iths.library.exception.NotFoundException;
import se.iths.library.models.QuantityToJson;
import se.iths.library.service.StockService;

import java.util.List;

@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/id/{id}")
    public Stock updateLogin(@RequestBody QuantityToJson quantityToJson, @PathVariable Long id) {
        return stockService.updateStockQuantity(quantityToJson.getQuantity(), id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public List<StockDTO> getAllItemStocks(){
        return stockService.getAllItemStocks();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/id/{id}")
    public Stock getStockByItemId(@PathVariable Long id){
        var stock = stockService.getStockByItemId(id);
        if (stock != null){
            return stock;
        }else {
            throw new NotFoundException("Item not found with id :" + id);
        }
    }
}
