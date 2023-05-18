package ru.jbimer.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jbimer.core.models.HtmlReportData;
import ru.jbimer.core.models.Project;
import ru.jbimer.core.services.HtmlReportService;

import java.util.List;
import java.util.Optional;

public interface HtmlReportDataRepository extends JpaRepository<HtmlReportData, Integer> {

    Optional<HtmlReportData> findByName(String fileName);

    List<HtmlReportData> findAllByProject(Project project);
}
