package se.iths.library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String barCode;
    @NotEmpty
    private String title;
    private String description;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "items_authors",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ItemLending> itemLendingSet;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Stock> stockSet;


    public Item(){
    }

    public Item(@NotEmpty String barCode, @NotEmpty String title, String description) {
        this.barCode = barCode;
        this.title = title;
        this.description = description;
    }

    public Item(@NotEmpty String barCode, @NotEmpty String title, String description, Set<Author> authors) {
        this.barCode = barCode;
        this.title = title;
        this.description = description;
        this.authors = authors;
    }

    public Item(@NotEmpty String barCode, @NotEmpty String title, String description, Set<Author> authors, Set<ItemLending> itemLendingSet, Set<Stock> stockSet) {
        this.barCode = barCode;
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.itemLendingSet = itemLendingSet;
        this.stockSet = stockSet;
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
