package ru.mitrofmep.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;

import javax.persistence.TypedQuery;
import java.util.*;


@Component
public class CollisionDAO {


    private final SessionFactory sessionFactory;

    @Autowired
    public CollisionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // get list of all collisions
    @Transactional(readOnly = true)
    public List<Collision> index() {
        Session session = sessionFactory.getCurrentSession();

        List<Collision> collisions = session.createQuery("select c from Collision c", Collision.class).getResultList();

        return collisions;
    }

    // get list of collisions, signed for one engineer
    @Transactional(readOnly = true)
    public List<Collision> index(int engineer_id) {
        Session session = sessionFactory.getCurrentSession();

        TypedQuery<Collision> query = session
                .createQuery("select c from Collision c join c.engineer e where e.id = :engineer_id", Collision.class)
                .setParameter("engineer_id", engineer_id);

        return query.getResultList();
    }


    // returns Collision object by its ID
    @Transactional(readOnly = true)
    public Collision show(int id) {
        Session session = sessionFactory.getCurrentSession();

        Collision collision = session.get(Collision.class, id);

        return collision;
    }


    // Save Collision to DB
    @Transactional
    public void save(Collision collision) {
        Session session = sessionFactory.getCurrentSession();

        session.save(collision);
    }


    // Update Collision
    @Transactional
    public void update(int id, Collision updatedCollision) {
        Session session = sessionFactory.getCurrentSession();

        Collision collisionToBeUpdated = session.get(Collision.class, id);

        collisionToBeUpdated.setDiscipline1(updatedCollision.getDiscipline1());
        collisionToBeUpdated.setDiscipline2(updatedCollision.getDiscipline2());
        collisionToBeUpdated.setElementName1(updatedCollision.getElementName1());
        collisionToBeUpdated.setElementName2(updatedCollision.getElementName2());
        collisionToBeUpdated.setElementId1(updatedCollision.getElementId1());
        collisionToBeUpdated.setElementId2(updatedCollision.getElementId2());
        collisionToBeUpdated.setStatus(updatedCollision.getStatus());
    }

    // Delete Collision
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();

        session.remove(session.get(Collision.class, id));
    }

    // Set engineer_id for Collision object. If passed 0 - means assign engineer - set null to DB
    @Transactional
    public void set(int collision_id, int engineer_id) {
        Session session = sessionFactory.getCurrentSession();

        Collision collision = session.get(Collision.class, collision_id);
        Engineer engineer = session.get(Engineer.class, engineer_id);

        collision.setEngineer(engineer);
    }

}
