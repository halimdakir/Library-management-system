package se.iths.library.models;

public class EmailJsonFormat {
    private String email;

    public EmailJsonFormat() {
    }

    public EmailJsonFormat(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
