package ru.mitrofmep.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mitrofmep.models.Engineer;

import java.util.List;
import java.util.Optional;


@Component
public class EngineerDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public EngineerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Engineer> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select e from Engineer e", Engineer.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Engineer show(int id) {
        Session session = sessionFactory.getCurrentSession();

        Engineer engineer = session.get(Engineer.class, id);

        return engineer;
    }

    @Transactional(readOnly = true)
    public Optional<Engineer> show(String email) {
        Session session = sessionFactory.getCurrentSession();

        Optional<Engineer> engineer = session
                .createQuery("select e from Engineer e where e.email = :email", Engineer.class)
                .setParameter("email", email).uniqueResultOptional();
        return engineer;
    }


    @Transactional
    public void save(Engineer engineer) {
        Session session = sessionFactory.getCurrentSession();

        session.save(engineer);
    }

    @Transactional
    public void update(int id, Engineer updatedEngineer) {
        Session session = sessionFactory.getCurrentSession();

        Engineer engineerToBeUpdated = session.get(Engineer.class, id);

        engineerToBeUpdated.setFirstName(updatedEngineer.getFirstName());
        engineerToBeUpdated.setLastName(updatedEngineer.getLastName());
        engineerToBeUpdated.setEmail(updatedEngineer.getEmail());
        engineerToBeUpdated.setDiscipline(updatedEngineer.getDiscipline());
        engineerToBeUpdated.setTelegramUsername(updatedEngineer.getTelegramUsername());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();

        session.remove(session.get(Engineer.class, id));
    }


}
