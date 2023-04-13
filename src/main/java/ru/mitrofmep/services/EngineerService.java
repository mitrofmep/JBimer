package ru.mitrofmep.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mitrofmep.dao.EngineerDAO;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;
import ru.mitrofmep.repositories.EngineersRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class EngineerService {

    private final EngineersRepository engineersRepository;
    private final EngineerDAO engineerDAO;

    @Autowired
    public EngineerService(EngineersRepository engineersRepository, EngineerDAO engineerDAO) {
        this.engineersRepository = engineersRepository;
        this.engineerDAO = engineerDAO;
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

    public Optional<Engineer> getEngineerByFullName(String firstName, String lastName) {
        return engineersRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Optional<Engineer> getEngineerByTelegramUsername(String telegramUsername) {
        return engineersRepository.findByTelegramUsername(telegramUsername);
    }

    public Optional<Engineer> getEngineerByEmail(String email) {
        return engineersRepository.findByEmail(email);
    }

    public Engineer findOne(int id) {
        Optional<Engineer> foundEngineer = engineersRepository.findById(id);

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
