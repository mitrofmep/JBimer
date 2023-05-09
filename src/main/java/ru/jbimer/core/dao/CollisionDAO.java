package ru.jbimer.core.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jbimer.core.models.Collision;

import java.util.Date;
import java.util.List;


@Component
public class CollisionDAO {

    private final EntityManager entityManager;

    @Autowired
    public CollisionDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveAll(List<Collision> collisions) {
        entityManager.getTransaction().begin();
        System.out.println("here1");
        for (Collision collision :
                collisions) {
            System.out.println("here2");
            Collision existingCollision =
                    (Collision) entityManager
                            .createQuery("select c from Collision c " +
                                    "where c.elementId1 = :elemId1 and c.elementId2 = :elemId2 or " +
                                    "c.elementId1 = :elemId2 and c.elementId2 = :elemId1")
                            .setParameter("elemId1", collision.getElementId1())
                            .setParameter("elemId2", collision.getElementId2())
                            .getSingleResult();
            System.out.println("collision is " + existingCollision);

            if (existingCollision != null) {
                existingCollision.setCreatedAt(new Date());
                entityManager.merge(existingCollision);
            } else {
                entityManager.persist(collision);
            }
        }
        entityManager.getTransaction().commit();
    }
}
