package se.iths.library.dto;

import se.iths.library.models.Roles;

public class UserInfoDTO {
    private Long userId;
    private Long loginId;
    private boolean active;
    private String fullName;
    private String email;
    private String password;
    private Roles roles;
    private String birthDate;
    private String address;

    public UserInfoDTO(boolean active, String fullName, String email, String password, Roles roles, String birthDate, String address, Long userId, Long loginId) {
        this.active = active;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.birthDate = birthDate;
        this.address = address;
        this.userId = userId;
        this.loginId = loginId;
    }

    public UserInfoDTO(Long userId, Long loginId, boolean active, String fullName, String email, String password, Roles roles, String birthDate, String address) {
        this.userId = userId;
        this.loginId = loginId;
        this.active = active;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.birthDate = birthDate;
        this.address = address;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }
    /*boolean isActive();
    String getFullName();
    String getEmail();
    String getRoles();
    String getBirthDate();
    String getAddress();*/
}
