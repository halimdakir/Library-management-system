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
    private boolean isConfirmed;
    private boolean isReturned;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public ItemLending() {
    }


    public ItemLending(@NotEmpty String creationDate, @NotEmpty String dueDate, boolean isConfirmed, boolean isReturned, User user, Item item) {
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.isConfirmed = isConfirmed;
        this.isReturned = isReturned;
        this.user = user;
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
    public boolean isReturned() {
        return isReturned;
    }
    public void setReturned(boolean returned) {
        isReturned = returned;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    public boolean isConfirmed() {
        return isConfirmed;
    }
    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
