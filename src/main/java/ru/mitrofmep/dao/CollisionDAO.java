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
    private static BeanPropertyRowMapper<Collision> collisionMapper;

    static {
        collisionMapper = new BeanPropertyRowMapper<>(Collision.class);
        collisionMapper.setPrimitivesDefaultedForNullValue(true);
    }

    @Autowired
    public CollisionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Collision> index() {
        return jdbcTemplate.query("SELECT * FROM collision", collisionMapper);
    }

    public List<Collision> index(int engineer_id) {
        return jdbcTemplate.query("SELECT * FROM collision WHERE engineer_id=?", new Object[]{engineer_id}, collisionMapper);
    }

    public Collision show(int id) {
        return jdbcTemplate.query("SELECT * FROM collision WHERE id = ?", new Object[]{id}, collisionMapper)
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

    public void set(int collision_id, int engineer_id) {
        if (engineer_id == 0) {
            jdbcTemplate.update("UPDATE collision SET engineer_id=NULL WHERE id=?", collision_id);
        } else jdbcTemplate.update("UPDATE collision SET engineer_id=? WHERE id=?",
                engineer_id, collision_id);
    }

}
