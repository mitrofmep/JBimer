package ru.jbimer.core.models;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Engineer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public String getFullNameWithDiscipline() {
        return firstName + ' ' + lastName + '-' + discipline;
    }
}
