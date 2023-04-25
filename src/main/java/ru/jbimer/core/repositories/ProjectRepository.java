package ru.jbimer.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jbimer.core.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
