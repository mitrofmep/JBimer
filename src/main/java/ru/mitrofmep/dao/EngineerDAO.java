package ru.mitrofmep.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mitrofmep.models.Engineer;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
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
