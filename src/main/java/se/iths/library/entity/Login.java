package se.iths.library.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private Boolean isAdmin;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Login() {
    }

    public Login(@NotEmpty String email, @NotEmpty String password, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Login(@NotEmpty String email, @NotEmpty String password, boolean isAdmin, User user) {
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
