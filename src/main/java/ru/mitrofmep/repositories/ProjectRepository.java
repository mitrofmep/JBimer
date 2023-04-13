package ru.mitrofmep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mitrofmep.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
