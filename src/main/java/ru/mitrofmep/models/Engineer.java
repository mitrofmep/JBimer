package ru.mitrofmep.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "engineer")
public class Engineer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Field is empty")
    @Column(name = "firstname")
    private String firstName;

    @NotEmpty(message = "Field is empty")
    @Column(name = "lastname")
    private String lastName;

    @NotEmpty(message = "Field is empty")
    @Pattern(regexp = "\\D+",
            message = "Enter correct name of service, ex. OV")
    @Column(name = "service")
    private String service;

    @NotEmpty (message = "Field is empty")
    @Email
    @Column(name = "email")
    private String email;

    // Telegram username
    @NotEmpty (message = "Field is empty")
    @Column(name = "tgusername")
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
