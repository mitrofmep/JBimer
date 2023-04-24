package ru.mitrofmep.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mitrofmep.dao.CollisionDAO;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;
import ru.mitrofmep.repositories.CollisionsRepository;

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
//        return collisionsRepository.findAll(Sort.by("discipline1"));

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

    public Engineer getCollisionEngineer(int id) {
        return collisionsRepository.findById(id).map(Collision::getEngineer).orElse(null);
    }

    public List<Collision> findWithPagination(Integer page, Integer collisionsPerPage) {
        return collisionsRepository.findAll(PageRequest.of(page, collisionsPerPage)).getContent();
    }

    public List<Collision> searchByDiscipline(String query) {
        return collisionsRepository.findByAnyDiscipline(query);
    }

    @Transactional
    public void markAsFake(int id) {
        collisionsRepository.findById(id).ifPresent(
                collision -> {
                    collision.setFake(true);
                }
        );
    }
}
