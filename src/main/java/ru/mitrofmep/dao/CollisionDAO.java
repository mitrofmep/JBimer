package ru.mitrofmep.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;

import java.util.List;
import java.util.Optional;


@Component
public class CollisionDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CollisionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Collision> index() {
        return jdbcTemplate.query("SELECT * FROM collision", new BeanPropertyRowMapper<>(Collision.class));
    }

    public Collision show(int id) {
        return jdbcTemplate.query("SELECT * FROM collision WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Collision.class))
                .stream().findAny().orElse(null);
    }


    public void save(Collision collision) {
        jdbcTemplate.update("INSERT INTO collision (status, service, idelement1, idelement2) VALUES (?, ?, ?, ?)",
                collision.getStatus(), collision.getService(), collision.getIdElement1(), collision.getIdElement2());    }

    public void update(int id, Collision updatedCollision) {
        jdbcTemplate.update("UPDATE collision SET status=?, service=?, idelement1=?, idelement2=? WHERE id=?", updatedCollision.getStatus(),
                updatedCollision.getService(), updatedCollision.getIdElement1(),
                updatedCollision.getIdElement2(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM collision WHERE id=?", id);
    }

}
