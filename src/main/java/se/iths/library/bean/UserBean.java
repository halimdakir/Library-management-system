package se.iths.library.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.domain.LendingItemsDomain;
import se.iths.library.entity.ItemLending;
import se.iths.library.service.ItemLendingService;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@ViewScoped
public class UserBean implements Serializable {
    private Long id;
    private List<ItemLending> lendingItemsList = new ArrayList<>();


    @Autowired
    ItemLendingService itemService;


    public void getLendingItems(){

    }

    public List<ItemLending> getLendingItemsList() {
        return lendingItemsList;
    }

    public void setLendingItemsList(List<ItemLending> lendingItemsList) {
        this.lendingItemsList = lendingItemsList;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
