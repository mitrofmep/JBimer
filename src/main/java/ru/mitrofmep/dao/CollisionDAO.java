package ru.mitrofmep.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;

import java.util.*;


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

    // get list of all collisions
    public List<Collision> index() {
        return jdbcTemplate.query("SELECT * FROM collision", collisionMapper);
    }

    // get list of collisions, signed for one engineer
    public List<Collision> index(int engineer_id) {
        return jdbcTemplate.query("SELECT * FROM collision WHERE engineer_id=?", new Object[]{engineer_id}, collisionMapper);
    }


    // get Map<Engineer's id, Number of collisions> - for Engineer's index page to show the total amount of collisions for every engineer
    // Better refactor with JOIN, not to make multiple requests
    public Map<Integer, Integer> getCollisionsPerPerson() {
        List<Integer> personIds = getAllPersonIds();

        Map<Integer, Integer> collisionsPerPerson = new HashMap<>();
        for (int personId : personIds) {
            List<Collision> collisions = index(personId);
            int numCollisions = collisions.size();
            collisionsPerPerson.put(personId, numCollisions);
        }
        return collisionsPerPerson;
    }

    // get list of all Engineer ID's in DB Engineer
    public List<Integer> getAllPersonIds() {
        List<Integer> personIds = jdbcTemplate.queryForList("SELECT DISTINCT id FROM engineer", Integer.class);
        return personIds;
    }

    // returns Collision object by its ID
    public Collision show(int id) {
        return jdbcTemplate.query("SELECT * FROM collision WHERE id = ?", new Object[]{id}, collisionMapper)
                .stream().findAny().orElse(null);
    }


    // Save Collision to DB
    public void save(Collision collision) {
        jdbcTemplate.update("INSERT INTO collision (status, service, idelement1, idelement2) VALUES (?, ?, ?, ?)",
                collision.getStatus(), collision.getService(), collision.getIdElement1(), collision.getIdElement2());    }


    // Update Collision
    public void update(int id, Collision updatedCollision) {
        jdbcTemplate.update("UPDATE collision SET status=?, service=?, idelement1=?, idelement2=? WHERE id=?", updatedCollision.getStatus(),
                updatedCollision.getService(), updatedCollision.getIdElement1(),
                updatedCollision.getIdElement2(), id);
    }

    // Delete Collision
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM collision WHERE id=?", id);
    }

    // Set engineer_id for Collision object. If passed 0 - means assign engineer - set null to DB
    public void set(int collision_id, int engineer_id) {
        if (engineer_id == 0) {
            jdbcTemplate.update("UPDATE collision SET engineer_id=NULL WHERE id=?", collision_id);
        } else jdbcTemplate.update("UPDATE collision SET engineer_id=? WHERE id=?",
                engineer_id, collision_id);
    }

}
