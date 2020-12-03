package se.iths.library.controller;

import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Item;
import se.iths.library.service.ItemService;

import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;
    public ItemController(ItemService itemService){
        this.itemService=itemService;
    }

    @GetMapping("/id/{id}")
    public Optional<Item> getOneItemById(@PathVariable Long id){
        return itemService.getItemById(id);
    }
    @GetMapping("/all")
    public Iterable<Item> getAllItems(){
        return itemService.getAllItems();
    }
    @PostMapping("/new")
    public Item createNewItem(@RequestBody Item item) { return itemService.createItem(item);    }
    @DeleteMapping("/id/{id}")
    public void deleteItemById(@PathVariable Long id){ itemService.deleteItemById(id);    }

}
