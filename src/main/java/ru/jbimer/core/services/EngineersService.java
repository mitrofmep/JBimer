package ru.jbimer.core.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jbimer.core.dao.EngineerDAO;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.repositories.EngineersRepository;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.security.EngineerDetails;

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

    public Engineer findOneAndItsCollisions(int id) {
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
