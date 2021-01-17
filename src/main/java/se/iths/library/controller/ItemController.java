package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Item;
import se.iths.library.exception.DeleteDetails;
import se.iths.library.exception.NotFoundException;
import se.iths.library.exception.UnprocessableEntityException;
import se.iths.library.service.ItemService;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PreAuthorize("permitAll()")
    @GetMapping("/id/{id}")
    public Optional<Item> getOneItemById(@PathVariable Long id){
        var item = itemService.getItemById(id);
        if (item.isPresent()){
            return item;
        }else {
            throw new NotFoundException("Item not found with id :" + id);
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public Iterable<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/new")
    public Item createNewItem(@RequestBody Item item) {
        if (item.getTitle() != null && item.getBarCode() != null){
            return itemService.createItem(item);
        }else {
            throw new UnprocessableEntityException("Title & BarCode are required!");
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/id/{id}")
    public Item updateItem(@RequestBody Item item , Long id) {
        return itemService.updateItem(item, id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable Long id){
        var item = itemService.getItemById(id);
        if (item.isPresent()){
            itemService.deleteItemById(id);
            return new ResponseEntity<>(new DeleteDetails("Delete request", "Item with id :"+id+" is successfully deleted!"), HttpStatus.OK);
        }else {
            throw new NotFoundException("Item not found with id :" + id);
        }
    }

}
