package ru.mitrofmep.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Collision")
public class Collision {

    @Id
    @Column(name = "collision_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project projectBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineer_id", referencedColumnName = "engineer_id")
    private Engineer engineer;

    @Column(name = "discipline_1")
    private String discipline1;

    @Column(name = "discipline_2")
    private String discipline2;

    @Column(name = "element_name_1")
    private String elementName1;

    @Column(name = "element_name_2")
    private String elementName2;

    @Column(name = "element_id_1")
    private int elementId1;

    @Column(name = "element_id_2")
    private int elementId2;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "fake")
    private boolean fake;

    public Collision(Project projectBase, String discipline1, String discipline2, String elementName1, String elementName2, int elementId1, int elementId2, String status) {
        this.projectBase = projectBase;
        this.discipline1 = discipline1;
        this.discipline2 = discipline2;
        this.elementName1 = elementName1;
        this.elementName2 = elementName2;
        this.elementId1 = elementId1;
        this.elementId2 = elementId2;
        this.status = status;
    }

    public Collision() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProjectBase() {
        return projectBase;
    }

    public void setProjectBase(Project projectBase) {
        this.projectBase = projectBase;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public String getDiscipline1() {
        return discipline1;
    }

    public void setDiscipline1(String discipline1) {
        this.discipline1 = discipline1;
    }

    public String getDiscipline2() {
        return discipline2;
    }

    public void setDiscipline2(String discipline2) {
        this.discipline2 = discipline2;
    }

    public String getElementName1() {
        return elementName1;
    }

    public void setElementName1(String elementName1) {
        this.elementName1 = elementName1;
    }

    public String getElementName2() {
        return elementName2;
    }

    public void setElementName2(String elementName2) {
        this.elementName2 = elementName2;
    }

    public int getElementId1() {
        return elementId1;
    }

    public void setElementId1(int elementId1) {
        this.elementId1 = elementId1;
    }

    public int getElementId2() {
        return elementId2;
    }

    public void setElementId2(int elementId2) {
        this.elementId2 = elementId2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    public String getFullInfoString() {
        return status + "---" + discipline1 + '-' + elementName1 + '-' + elementId1 + "---"
                + discipline2 + '-' + elementName2 + '-' + elementId2;
    }
}
