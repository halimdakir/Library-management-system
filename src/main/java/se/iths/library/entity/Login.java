package se.iths.library.entity;

import se.iths.library.domain.Roles;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private boolean active;
    private Roles roles;


    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "login", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ItemLending> itemLendingSet;

    public Login() {
    }

    public Login(@NotEmpty String email, @NotEmpty String password, Roles roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Login(@NotEmpty String email, @NotEmpty String password, Roles roles, User user) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ItemLending> getItemLendingSet() {
        return itemLendingSet;
    }

    public void setItemLendingSet(Set<ItemLending> itemLendingSet) {
        this.itemLendingSet = itemLendingSet;
    }
}
