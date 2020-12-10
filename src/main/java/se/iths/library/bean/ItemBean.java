package se.iths.library.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.controller.ItemController;
import se.iths.library.entity.Item;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@ViewScoped
public class ItemBean {
    private Long id;
    private String barCode;
    private String title;
    private List<Item> itemList = new ArrayList<>();
    private Item selectedItem;

    @Autowired
    ItemController itemController;

    public void getAllItems(){
        Iterable<Item> iterable = itemController.getAllItems();
        itemList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
    public String itemPage(){
        getAllItems();
        return "item";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }
}
