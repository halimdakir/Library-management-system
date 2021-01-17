package se.iths.library.entity;

import se.iths.library.models.Roles;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    private boolean active;
    @Enumerated(EnumType.STRING)
    private Roles roles;


    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "login")
    private User user;



    public Login() {
    }

    public Login(@NotEmpty String email, @NotEmpty String password, Roles roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Login(@NotEmpty String email, boolean active, Roles roles) {
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    public Login(@NotEmpty String email, @NotEmpty String password, boolean active, Roles roles) {
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }



    public Login(@NotEmpty String password) {
        this.password = password;
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
}
