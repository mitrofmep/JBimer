package ru.mitrofmep.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Engineer")
public class Engineer {

    @Id
    @Column(name = "engineer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Field is empty")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Field is empty")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Field is empty")
    @Pattern(regexp = "\\D+",
            message = "Enter correct name of service, ex. OV")
    @Column(name = "discipline")
    private String discipline;

    @NotEmpty (message = "Field is empty")
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty (message = "Field is empty")
    @Column(name = "telegram_username")
    private String telegramUsername;

    @ManyToMany(mappedBy = "engineersOnProject")
    private List<Project> projects;

    @OneToMany(mappedBy = "engineer", fetch = FetchType.EAGER)
    private List<Collision> collisions;

    public Engineer() {
    }

    public Engineer(String firstName, String lastName, String service, String email, String tgUsername) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.discipline = service;
        this.email = email;
        this.telegramUsername = tgUsername;
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

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String service) {
        this.discipline = service;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public void setTelegramUsername(String tgUsername) {
        this.telegramUsername = tgUsername;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Collision> getCollisions() {
        return collisions;
    }

    public void setCollisions(List<Collision> collisions) {
        this.collisions = collisions;
    }
}
