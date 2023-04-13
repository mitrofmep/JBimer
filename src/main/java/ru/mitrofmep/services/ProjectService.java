package ru.mitrofmep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mitrofmep.models.Project;
import ru.mitrofmep.repositories.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
