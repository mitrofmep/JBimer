package ru.jbimer.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "html_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HtmlReportData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "uploaded_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedAt;

    @Column(name = "data")
    private String data;

    @ManyToOne
    @JoinColumn(name = "engineer_id", referencedColumnName = "engineer_id")
    private Engineer engineer;
}
