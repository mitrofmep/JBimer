package ru.mitrofmep.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Project")
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Field is empty")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "projectBase", fetch = FetchType.EAGER)
    private List<Collision> collisionsOnProject;

    @ManyToMany
    @JoinTable(
            name = "Project_Engineer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_id")
    )
    private List<Engineer> engineersOnProject;

    public Project(){}

    public Project(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Collision> getCollisionsOnProject() {
        return collisionsOnProject;
    }

    public void setCollisionsOnProject(List<Collision> collisionsOnProject) {
        this.collisionsOnProject = collisionsOnProject;
    }

    public List<Engineer> getEngineersOnProject() {
        return engineersOnProject;
    }

    public void setEngineersOnProject(List<Engineer> engineersOnProject) {
        this.engineersOnProject = engineersOnProject;
    }
}
