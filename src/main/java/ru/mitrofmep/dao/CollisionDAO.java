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


    // get list of all collisions
    public List<Collision> index() {
        return null;
    }

    // get list of collisions, signed for one engineer
    public List<Collision> index(int engineer_id) {
        return null;
    }


    // get Map<Engineer's id, Number of collisions> - for Engineer's index page to show the total amount of collisions for every engineer
    // Better refactor with JOIN, not to make multiple requests
    public Map<Integer, Integer> getCollisionsPerPersons() {
        return null;
    }

    // get list of all Engineer ID's in DB Engineer
    public List<Integer> getAllPersonIds() {
        return null;
    }

    // returns Collision object by its ID
    public Collision show(int id) {
        return null;
    }


    // Save Collision to DB
    public void save(Collision collision) {
        }


    // Update Collision
    public void update(int id, Collision updatedCollision) {

    }

    // Delete Collision
    public void delete(int id) {

    }

    // Set engineer_id for Collision object. If passed 0 - means assign engineer - set null to DB
    public void set(int collision_id, int engineer_id) {

    }

}
