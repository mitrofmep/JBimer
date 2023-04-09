package ru.mitrofmep.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mitrofmep.models.Engineer;

import java.util.List;
import java.util.Map;
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

        List<Engineer> engineers = session.createQuery("select e from Engineer e", Engineer.class).getResultList();

        return engineers;
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

        session.persist(engineer);
    }

    public void update(int id, Engineer updatedEngineer) {
    }

    public void delete(int id) {
    }


}
