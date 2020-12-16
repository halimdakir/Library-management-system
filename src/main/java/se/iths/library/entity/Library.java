package se.iths.library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String name;
    private String address;

    @OneToMany(mappedBy = "Library", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Stock> stockSet;

    public Library() {
    }

    public Library(@NotEmpty String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Library(@NotEmpty String name, String address, Set<Stock> stockSet) {
        this.name = name;
        this.address = address;
        this.stockSet = stockSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
