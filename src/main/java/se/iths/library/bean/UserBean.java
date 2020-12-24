package se.iths.library.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.models.BorrowedItemsDTO;
import se.iths.library.service.ItemLendingService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class UserBean {
    private String email;
    private Long id;
    private List<BorrowedItemsDTO> borrowedItemList = new ArrayList<>();


    @Autowired
     ItemLendingService itemLendingService;


    public void getBorrowedItems(String email){
        borrowedItemList = itemLendingService.findBorrowedItemsAndCreationDueDateByUserId(email);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BorrowedItemsDTO> getBorrowedItemList() {
        return borrowedItemList;
    }

    public void setBorrowedItemList(List<BorrowedItemsDTO> borrowedItemList) {
        this.borrowedItemList = borrowedItemList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
