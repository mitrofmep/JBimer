package ru.jbimer.core.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Project")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Field is empty")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "projectBase", fetch = FetchType.LAZY)
    private List<Collision> collisionsOnProject;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Project_Engineer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_id")
    )
    private List<Engineer> engineersOnProject;
}
