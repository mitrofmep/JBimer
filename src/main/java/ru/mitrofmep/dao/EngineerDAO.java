package ru.mitrofmep.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mitrofmep.models.Engineer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class EngineerDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EngineerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Engineer> index() {
        return jdbcTemplate.query("SELECT * FROM engineer", new BeanPropertyRowMapper<>(Engineer.class));
    }

    public Engineer show(int id) {
        return jdbcTemplate.query("SELECT * FROM engineer WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Engineer.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Engineer> show(String email) {
        return jdbcTemplate.query("SELECT * FROM engineer WHERE email=?",
                new Object[]{email}, new BeanPropertyRowMapper<>(Engineer.class))
                .stream().findAny();
    }


    public void save(Engineer engineer) {
        jdbcTemplate.update("INSERT INTO engineer (firstname, lastname, service, email, tgusername) VALUES (?, ?, ?, ?, ?)",
                engineer.getFirstName(), engineer.getLastName(), engineer.getService(), engineer.getEmail(), engineer.getTgUsername());
    }

    public void update(int id, Engineer updatedEngineer) {
        jdbcTemplate.update("UPDATE engineer SET firstname=?, lastname=?, service=?, email=?, tgusername=? WHERE id=?", updatedEngineer.getFirstName(),
                updatedEngineer.getLastName(), updatedEngineer.getService(),
                updatedEngineer.getEmail(), updatedEngineer.getTgUsername(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM engineer WHERE id=?", id);
    }




}
