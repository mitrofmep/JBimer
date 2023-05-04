package ru.jbimer.core.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Collision")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private String elementId1;

    @Column(name = "element_id_2")
    private String elementId2;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "fake")
    private boolean fake;


    public String getFullInfoString() {
        return status + "---" + discipline1 + '-' + elementName1 + '-' + elementId1 + "---"
                + discipline2 + '-' + elementName2 + '-' + elementId2;
    }
}
