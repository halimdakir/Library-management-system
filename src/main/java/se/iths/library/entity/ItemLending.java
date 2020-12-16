package se.iths.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class ItemLending {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @JsonFormat(pattern = "yyyy-mm-dd", shape = JsonFormat.Shape.STRING)
    private String creationDate;
    @NotEmpty
    @JsonFormat(pattern = "yyyy-mm-dd", shape = JsonFormat.Shape.STRING)
    private String dueDate;
    @NotEmpty
    @JsonFormat(pattern = "yyyy-mm-dd", shape = JsonFormat.Shape.STRING)
    private String returnDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "login_id", nullable = false)
    private Login login;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public ItemLending() {
    }

    public ItemLending(@NotEmpty String creationDate, @NotEmpty String dueDate, @NotEmpty String returnDate, Login login, Item item) {
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.login = login;
        this.item = item;
    }

    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public String getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public Login getLogin() {
        return login;
    }
    public void setLogin(Login login) {
        this.login = login;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
