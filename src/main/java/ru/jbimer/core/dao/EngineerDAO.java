package ru.jbimer.core.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.jbimer.core.models.Engineer;

import java.util.HashSet;
import java.util.Set;


@Component
public class EngineerDAO {

    private final EntityManager entityManager;

    @Autowired
    public EngineerDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public Set<Engineer> index() {
        Session session = entityManager.unwrap(Session.class);

        Set<Engineer> engineers = new HashSet<Engineer>(session.createQuery("select e from Engineer e " +
                        "left join fetch e.collisions ")
                .getResultList());

        return engineers;
    }
}
