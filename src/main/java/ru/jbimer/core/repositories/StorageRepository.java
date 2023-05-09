package ru.jbimer.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jbimer.core.models.HtmlReportData;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<HtmlReportData, Integer> {

    Optional<HtmlReportData> findByName(String fileName);
}
