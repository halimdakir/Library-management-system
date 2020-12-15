package se.iths.library.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.entity.Item;
import se.iths.library.service.ItemService;

import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@ViewScoped
public class UserBean implements Serializable {
    private Long id;
    private List<Item> lendingItemsList = new ArrayList<>();

    @Autowired
    ItemService itemService;


    public void getLendingItems(Long id){
        Iterable<Item> iterable = itemService.findLendingItemsByUserId(id);
        lendingItemsList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Item> getLendingItemsList() {
        return lendingItemsList;
    }
    public void setLendingItemsList(List<Item> lendingItemsList) {
        this.lendingItemsList = lendingItemsList;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
