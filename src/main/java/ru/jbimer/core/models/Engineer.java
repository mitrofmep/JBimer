package ru.jbimer.core.models;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Engineer")
public class Engineer {

    @Id
    @Column(name = "engineer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 1, max = 30, message = "Field is empty")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 1, max = 30, message = "Field is empty")
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 1, max = 30, message = "Field is empty")
    @Pattern(regexp = "\\D+",
            message = "Enter correct name of service, ex. OV")
    @Column(name = "discipline")
    private String discipline;

    @Size(min = 1, max = 30, message = "Field is empty")
    @Column(name = "email")
    private String email;

    @Size(min = 1, max = 30, message = "Field is empty")
    @Column(name = "telegram_username")
    private String telegramUsername;

    @Size(min = 1, max = 30, message = "Field is empty")
    @Column(name = "username")
    private String username;

    @Size(min = 1, message = "Field is empty")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "engineersOnProject", fetch = FetchType.LAZY)
    private List<Project> projects;

    @OneToMany(mappedBy = "engineer", fetch = FetchType.LAZY)
    private List<Collision> collisions;

    @OneToMany(mappedBy = "engineer", fetch = FetchType.LAZY)
    private List<HtmlReportData> reports;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getFullNameWithDiscipline() {
        return firstName + ' ' + lastName + '-' + discipline;
    }
}
