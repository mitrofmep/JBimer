package ru.mitrofmep.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.util.Optional;


@Component
public class CollisionDAO {
    private final EntityManager entityManager;

    @Autowired
    public CollisionDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
