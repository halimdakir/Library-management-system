package se.iths.library.dto;

public class StockDTO {
    private Long id;
    private int quantity;
    private String barCode;
    private String title;


    public StockDTO() {
    }

    public StockDTO(Long id, int quantity, String barCode, String title) {
        this.id = id;
        this.quantity = quantity;
        this.barCode = barCode;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

}
