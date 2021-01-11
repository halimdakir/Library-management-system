package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Item;
import se.iths.library.service.ItemService;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Secured("permitAll()")
    @GetMapping("/id/{id}")
    public Optional<Item> getOneItemById(@PathVariable Long id){
        return itemService.getItemById(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public Iterable<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/new")
    public Item createNewItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/id/{id}")
    public Item updateItem(@RequestBody Item item , Long id) {
        return itemService.updateItem(item, id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/id/{id}")
    public void deleteItemById(@PathVariable Long id){ itemService.deleteItemById(id);    }

}
