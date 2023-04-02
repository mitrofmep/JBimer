package ru.mitrofmep.models;


import javax.validation.constraints.*;

public class Engineer {

    private int id;

    @NotEmpty(message = "Field is empty")
    @Size(min = 2, max = 30, message = "Maximum 30 characters")
    private String firstName;

    @NotEmpty(message = "Field is empty")
    @Size(min = 2, max = 30, message = "Maximum 30 characters")
    private String lastName;

    // Раздел проектирования
    @NotEmpty(message = "Field is empty")
    @Pattern(regexp = "\\D+",
            message = "Enter correct name of service, ex. OV")
    private String service;

    @NotEmpty (message = "Field is empty")
    @Email
    private String email;

    // Telegram username
    @NotEmpty (message = "Field is empty")
    private String tgUsername;


    public Engineer() {
    }

    public Engineer(String firstName, String lastName, String service, String email, String tgUsername) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.service = service;
        this.email = email;
        this.tgUsername = tgUsername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTgUsername() {
        return tgUsername;
    }

    public void setTgUsername(String tgUsername) {
        this.tgUsername = tgUsername;
    }
}
