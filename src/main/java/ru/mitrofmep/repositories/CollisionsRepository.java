package ru.mitrofmep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;

import java.util.List;

@Repository
public interface CollisionsRepository extends JpaRepository<Collision, Integer> {

    @Query("SELECT c FROM Collision c WHERE c.discipline1 = :discipline OR c.discipline2 = :discipline")
    List<Collision> findByAnyDiscipline(@Param("discipline") String discipline);

    // engineer.getCollisions()
    List<Collision> findByEngineer(Engineer engineer);
}
