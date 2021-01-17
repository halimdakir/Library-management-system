package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.library.exception.NotAcceptedException;
import se.iths.library.exception.NotFoundException;
import se.iths.library.service.ItemLendingService;

@RestController
@RequestMapping("borrowed")
public class ItemLendingController {

    @Autowired
    private ItemLendingService itemLendingService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/id/{id}")
    public ResponseEntity<?> returnBorrowedItem(@PathVariable Long id){
        var itemLending = itemLendingService.findItemLendingById(id);
        if (itemLending != null){

            if (! itemLending.isReturned()){
                itemLendingService.returnBorrowedItem(id);
                return new ResponseEntity<>("The borrowed Item is returned!", HttpStatus.ACCEPTED);

            }else {
                throw new NotAcceptedException("You have already returned this item!");
            }

        }else {
            throw new NotFoundException("The borrowed item not found with id :" + id);
        }
    }

}
