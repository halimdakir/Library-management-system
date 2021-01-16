package se.iths.library.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;

public class UserDomain {
    private String fullName;
    private String birthDate;
    private String address;
    private LoginDomain loginDomain;

    public UserDomain() {
    }

    public UserDomain(String fullName, String birthDate, String address, LoginDomain loginDomain) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.loginDomain = loginDomain;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public LoginDomain getLoginDomain() {
        return loginDomain;
    }

    public void setLoginDomain(LoginDomain loginDomain) {
        this.loginDomain = loginDomain;
    }
}
