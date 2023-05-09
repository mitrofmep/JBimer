package ru.jbimer.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jbimer.core.dao.CollisionDAO;
import ru.jbimer.core.models.Project;
import ru.jbimer.core.repositories.CollisionsRepository;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CollisionsService {

    private final CollisionsRepository collisionsRepository;
    private final CollisionDAO collisionDAO;
    private final ProjectService projectService;

    @Autowired
    public CollisionsService(CollisionsRepository collisionsRepository, CollisionDAO collisionDAO, ProjectService projectService) {
        this.collisionsRepository = collisionsRepository;
        this.collisionDAO = collisionDAO;
        this.projectService = projectService;
    }

    public List<Collision> findByEngineer(Engineer engineer) {
        return collisionsRepository.findByEngineer(engineer);
    }

    public List<Collision> findAll() {

        return collisionsRepository.findAllFetchEngineers();
    }


    public Collision findOne(int id) {
        Optional<Collision> foundCollision = collisionsRepository.findById(id);

        return foundCollision.orElse(null);
    }

    public Collision findOneAndEngineer(int id) {
        Optional<Collision> foundCollision = collisionsRepository.findByIdFetchEngineer(id);

        return foundCollision.orElse(null);
    }

    @Transactional
    public void save(Collision collision) {

        collisionsRepository.save(collision);
    }

    @Transactional
    public void saveAll(List<Collision> collisions) {
        String disc1 = collisions.get(0).getDiscipline1();
        String disc2 = collisions.get(0).getDiscipline2();
        Date currDate = collisions.get(0).getCreatedAt();
        for (Collision collision :
                collisions) {
            Optional<Collision> foundCollision = collisionsRepository
                    .findByIdsForValidation(collision.getElementId1(), collision.getElementId2());
            if (foundCollision.isEmpty()) save(collision);
            else {
                foundCollision.get().setCreatedAt(new Date());
            }
        }
        update(currDate, disc1, disc2);
    }

    @Transactional
    public void update(Date date, String disc1, String disc2) {
        collisionsRepository.updateStatus(date, disc1, disc2);
    }

    @Transactional
    public void delete(int id) {
        collisionsRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        collisionsRepository.findByIdFetchEngineer(id).ifPresent(
                collision -> {
                    collision.setEngineer(null);
                }
        );
    }

    @Transactional
    public void assign(int id, Engineer selectedEngineer) {
        collisionsRepository.findByIdFetchEngineer(id).ifPresent(
                collision -> {
                    collision.setEngineer(selectedEngineer);
                }
        );
    }

    @Transactional
    public void addComment(int id, Engineer selectedEngineer, String comment) {
        Optional<Collision> optionalCollision = collisionsRepository.findByIdFetchEngineer(id);
        if (optionalCollision.isPresent()) {
            Collision collision = optionalCollision.get();
            String currComment = collision.getComment();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            String formattedDate = dateFormat.format(currentDate);
            if (currComment == null) currComment = formattedDate + ": "+ selectedEngineer.getFullNameWithDiscipline() + ": " + comment + "#@";
            else currComment = currComment + formattedDate + ": " + selectedEngineer.getFullNameWithDiscipline() + ": " + comment + "#@";
            collision.setComment(currComment);
        }
    }

    public Engineer getCollisionEngineer(int id) {
        return collisionsRepository.findById(id).map(Collision::getEngineer).orElse(null);
    }

    public Page<Collision> findAllWithPagination(int project_id, Pageable paging) {
        Project foundProject = projectService.findOne(project_id);
        return collisionsRepository.findByProjectBase(foundProject, paging);
    }

    public Page<Collision> searchByDiscipline(String keyword, int project_id, Pageable pageable) {
        Project foundProject = projectService.findOne(project_id);
        return collisionsRepository.findByDisciplinesAndProject(keyword, keyword, foundProject, pageable);
    }

    @Transactional
    public void markAsFake(int id) {
        collisionsRepository.findById(id).ifPresent(
                collision -> {
                    collision.setFake(true);
                }
        );
    }

    @Transactional
    public void markAsNotFake(int id) {
        collisionsRepository.findById(id).ifPresent(
                collision -> {
                    collision.setFake(false);
                }
        );
    }
}
