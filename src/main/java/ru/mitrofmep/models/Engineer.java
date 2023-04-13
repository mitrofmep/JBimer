package ru.mitrofmep.models;



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

    @NotNull(message = "Field is empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Field is empty")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Field is empty")
    @Pattern(regexp = "\\D+",
            message = "Enter correct name of service, ex. OV")
    @Column(name = "discipline")
    private String discipline;

    @NotNull (message = "Field is empty")
    @Column(name = "email")
    private String email;

    @NotNull (message = "Field is empty")
    @Column(name = "telegram_username")
    private String telegramUsername;

    @ManyToMany(mappedBy = "engineersOnProject")
    private List<Project> projects;

    @OneToMany(mappedBy = "engineer")
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

    public String getFullNameWithDiscipline() {
        return firstName + ' ' + lastName + '-' + discipline;
    }
}
