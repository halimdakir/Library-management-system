package se.iths.library.models;

public class LendingItemsDomain {
    private String barCode;
    private String title;
    private String creationDate;
    private String dueDate;

    public LendingItemsDomain(String barCode, String title, String creationDate, String dueDate) {
        this.barCode = barCode;
        this.title = title;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }

    public LendingItemsDomain(String creationDate, String dueDate) {
        this.creationDate = creationDate;
        this.dueDate = dueDate;
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
}
