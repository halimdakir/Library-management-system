package se.iths.library.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.entity.Author;
import se.iths.library.entity.Item;
import se.iths.library.service.AuthorService;
import se.iths.library.service.ItemService;

import javax.annotation.PostConstruct;
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
    private List<Author> authorList = new ArrayList<>();
    private Item selectedItem;
    private String search;
    private String keyWord;



    @Autowired
    ItemService itemService;
    @Autowired
    AuthorService authorService;


    @PostConstruct
    public void init(){
        getAllItems();
    }


    public void getAllItems(){
        Iterable<Item> iterable = itemService.getAllItems();
        itemList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
    public String itemPage(){
        getAllItems();
        return "item";

    }
    public void getItemByTitle(String word){
        switch (search) {
            case "Title":
                if (word == null || word.equals("")) {
                    Iterable<Item> iterable = itemService.getAllItems();
                    itemList = StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
                } else {
                    Iterable<Item> foundItem = itemService.findItemByTitle(word);
                    itemList = StreamSupport.stream(foundItem.spliterator(), false)
                            .collect(Collectors.toList());
                }
                break;
            case "Author":
                if (word == null || word.equals("")) {
                    Iterable<Item> iterable = itemService.getAllItems();
                    itemList = StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
                } else {
                    Iterable<Item> foundItem = itemService.findItemByAuthorName(word);
                    itemList = StreamSupport.stream(foundItem.spliterator(), false)
                            .collect(Collectors.toList());
                }

            case "Category":

                break;
        }
    }
    public void getAuthors(Long id){
        Iterable<Author> iterable = authorService.findAuthorByItemsTitle(id);
        authorList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    //<editor-fold desc="Getter & Setter">


    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
    //</editor-fold>
}
