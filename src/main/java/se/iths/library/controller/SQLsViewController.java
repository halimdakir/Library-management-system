package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.library.entity.CountBorrowedItemsView;
import se.iths.library.service.CountBorrowedItemsViewService;

import java.util.List;

@RestController
@RequestMapping("/view")
public class SQLsViewController {

    @Autowired
    CountBorrowedItemsViewService viewService;


    @GetMapping("/all")
    public List<CountBorrowedItemsView> countBorrowedItemsViewList(){
        return viewService.countBorrowedItemsViewList();
    }


}
