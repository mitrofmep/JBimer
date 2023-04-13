package ru.mitrofmep.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;
import ru.mitrofmep.repositories.EngineersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EngineerService {

    private final EngineersRepository engineersRepository;

    @Autowired
    public EngineerService(EngineersRepository engineersRepository) {
        this.engineersRepository = engineersRepository;
    }

    public List<Engineer> findAll() {
        return engineersRepository.findAll();
    }

    public List<Engineer> findAllSortedByCollisionsSize() {
        List<Engineer> engineers = engineersRepository.findAll(Sort.by("discipline", "firstName"));

//        engineers.sort((e1, e2) -> Integer.compare(e2.getCollisions().size(), e1.getCollisions().size()));

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
