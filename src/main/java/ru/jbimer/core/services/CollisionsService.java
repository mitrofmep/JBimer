package ru.jbimer.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jbimer.core.dao.CollisionDAO;
import ru.jbimer.core.repositories.CollisionsRepository;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional (readOnly = true)
public class CollisionsService {

    private final CollisionsRepository collisionsRepository;
    private final CollisionDAO collisionDAO;

    @Autowired
    public CollisionsService(CollisionsRepository collisionsRepository, CollisionDAO collisionDAO) {
        this.collisionsRepository = collisionsRepository;
        this.collisionDAO = collisionDAO;
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
        collision.setCreatedAt(new Date());


        collisionsRepository.save(collision);
    }

    @Transactional
    public void saveAll(List<Collision> collisions) {
        collisionsRepository.saveAll(collisions);
    }

    @Transactional
    public void update(int id, Collision updatedCollision) {
        Collision collisionToBeUpdated = collisionsRepository.findById(id).get();

        updatedCollision.setId(id);
        updatedCollision.setEngineer(collisionToBeUpdated.getEngineer());

        collisionsRepository.save(updatedCollision);
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

    public Page<Collision> findAllWithPagination(Pageable paging) {
        return collisionsRepository.findAll(paging);
    }

    public Page<Collision> searchByDiscipline(String keyword, Pageable pageable) {
        return collisionsRepository
                .findByDiscipline1ContainingIgnoreCaseOrDiscipline2ContainingIgnoreCaseOrderById(keyword, keyword, pageable);
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
