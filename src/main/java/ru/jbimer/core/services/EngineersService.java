package ru.jbimer.core.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jbimer.core.dao.EngineerDAO;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.repositories.EngineersRepository;
import ru.jbimer.core.models.Engineer;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class EngineersService{

    private final EngineersRepository engineersRepository;
    private final EngineerDAO engineerDAO;

    @Autowired
    public EngineersService(EngineersRepository engineersRepository, EngineerDAO engineerDAO) {
        this.engineersRepository = engineersRepository;
        this.engineerDAO = engineerDAO;
    }

    public Optional<Engineer> findByUsername(String username) {
        return engineersRepository.findByUsername(username);
    }

    public List<Engineer> findAll() {
        return engineersRepository.findAll();
    }

    public List<Engineer> findAllSortedByCollisionsSize() {
        List<Engineer> engineers = new ArrayList<>(engineerDAO.index());

        engineers.sort((e1, e2) -> {
            int collisionsComparison = Integer.compare(e2.getCollisions().size(), e1.getCollisions().size());
            if (collisionsComparison != 0) {
                return collisionsComparison;
            } else {
                return Integer.compare(e1.getId(), e2.getId());
            }
        });
        return engineers;
    }


    public Optional<Engineer> findByTelegramUsername(String telegramUsername) {
        return engineersRepository.findByTelegramUsername(telegramUsername);
    }

    public Optional<Engineer> findByEmail(String email) {
        return engineersRepository.findByEmail(email);
    }

    public Engineer findOne(int id) {
        Optional<Engineer> foundEngineer = engineersRepository.findById(id);

        return foundEngineer.orElse(null);
    }

    public Engineer findByIdFetchCollisions(int id) {
        Optional<Engineer> foundEngineer = engineersRepository.findByIdFetchCollisions(id);

        return foundEngineer.orElse(null);
    }

    @Transactional
    public void save(Engineer engineer) {
        engineersRepository.save(engineer);
    }

    @Transactional
    public void update(int id, Engineer updatedEngineer) {
        updatedEngineer.setId(id);
        engineersRepository.save(updatedEngineer);
    }

    @Transactional
    public void delete(int id) {
        engineersRepository.deleteById(id);
    }


    public List<Collision> getCollisionsByEngineerId(int id) {
        Optional<Engineer> engineer = engineersRepository.findById(id);

        if (engineer.isPresent()) {
            Hibernate.initialize(engineer.get().getCollisions());
            return engineer.get().getCollisions();
        }
        else {
            return Collections.emptyList();
        }
    }


}
