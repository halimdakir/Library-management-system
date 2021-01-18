package se.iths.library.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.dto.StockDTO;
import se.iths.library.entity.Author;
import se.iths.library.entity.Category;
import se.iths.library.entity.Item;
import se.iths.library.models.Categories;
import se.iths.library.service.AuthorService;
import se.iths.library.service.CategoryService;
import se.iths.library.service.ItemService;
import se.iths.library.service.StockService;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<StockDTO> stockDTOList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private Map<String, String> categories = new HashMap<>();
    private String category;



    @Autowired
    ItemService itemService;
    @Autowired
    AuthorService authorService;
    @Autowired
    StockService stockService;
    @Autowired
    CategoryService categoryService;


    public ItemBean(ItemService itemService) {
        this.itemService = itemService;
        getAllItems();
    }

    public void getAllItems(){
        Iterable<Item> iterable = itemService.getAllItems();
        itemList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());


        categories.put(Categories.BOOK.toString(), Categories.BOOK.toString());
        categories.put(Categories.DIGITAL_BOOK.toString(), Categories.DIGITAL_BOOK.toString());
        categories.put(Categories.DVD.toString(), Categories.DVD.toString());
        categories.put(Categories.IMAGE.toString(), Categories.IMAGE.toString());
    }
    public String itemPage(){
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
                break;

            case "Category":
                 categoryList = categoryService.getItemByCategory(convertStringToEnum(word));
                 itemList.clear();
                 for (Category category: categoryList){
                     var item = itemService.findItemById(category.getItem().getId());
                         itemList.add(item);
                 }
                break;
        }
    }
    private Categories convertStringToEnum(String word){
        Categories categories1 = null;
        if (word.equals(Categories.BOOK.toString())){
            categories1 = Categories.BOOK;
        }
        else if (word.equals(Categories.DVD.toString())){
            categories1 = Categories.DVD;
        }
        else if (word.equals(Categories.DIGITAL_BOOK.toString())){
            categories1 = Categories.DIGITAL_BOOK;
        }
        else if (word.equals(Categories.IMAGE.toString())){
            categories1 = Categories.IMAGE;
        }
        return categories1;
    }

    public void getItemStockBy(String word){
        switch (search) {
            case "Title":
                if (word == null || word.equals("")) {
                    stockDTOList = stockService.getAllItemStocks();
                } else {
                    stockDTOList = stockService.getItemStockByTitle(word);
                }
                break;
            case "Author":
                if (word == null || word.equals("")) {
                    stockDTOList = stockService.getAllItemStocks();
                } else {
                    stockDTOList = stockService.findItemStockByAuthorName(word);
                }

            case "Category":

                break;
        }
    }
    public void getAuthors(Long id){
        Iterable<Author> iterable = authorService.findAuthorByItemsId(id);
        authorList = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }


    //<editor-fold desc="Getter & Setter">


    public List<StockDTO> getStockDTOList() {
        return stockDTOList;
    }

    public void setStockDTOList(List<StockDTO> stockDTOList) {
        this.stockDTOList = stockDTOList;
    }

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


    public Map<String, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
    //</editor-fold>

}
