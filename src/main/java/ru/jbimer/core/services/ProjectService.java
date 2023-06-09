package ru.jbimer.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jbimer.core.repositories.CollisionsRepository;
import ru.jbimer.core.repositories.ProjectRepository;
import ru.jbimer.core.models.Project;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CollisionsRepository collisionsRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, CollisionsRepository collisionsRepository) {
        this.projectRepository = projectRepository;
        this.collisionsRepository = collisionsRepository;
    }


    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findOne(int id) {
        Optional<Project> foundProject = projectRepository.findById(id);

        return foundProject.orElse(null);
    }

    @Transactional
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Transactional
    public void update(int id, Project updatedProject) {
        updatedProject.setId(id);
        projectRepository.save(updatedProject);
    }

    @Transactional
    public void delete(int id) {
        projectRepository.deleteById(id);
    }
}
