package ru.jbimer.core.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CollisionDAO {

    private final EntityManager entityManager;

    @Autowired
    public CollisionDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
