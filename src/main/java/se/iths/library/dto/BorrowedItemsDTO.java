package se.iths.library.dto;

public class BorrowedItemsDTO {
    private Long id;
    private String title;
    private String barCode;
    private String creationDate;
    private String dueDate;

    public BorrowedItemsDTO(String title, String barCode, String creationDate, String dueDate) {
        this.title = title;
        this.barCode = barCode;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }

    public BorrowedItemsDTO(Long id, String title, String barCode, String creationDate, String dueDate) {
        this.id = id;
        this.title = title;
        this.barCode = barCode;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
