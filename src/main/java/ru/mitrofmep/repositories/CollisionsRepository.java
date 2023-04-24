package ru.mitrofmep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollisionsRepository extends JpaRepository<Collision, Integer> {

    @Query("SELECT c FROM Collision c LEFT JOIN FETCH c.engineer WHERE c.discipline1 = :discipline OR c.discipline2 = :discipline")
    List<Collision> findByAnyDiscipline(@Param("discipline") String discipline);

    @Query("SELECT c FROM Collision c LEFT JOIN FETCH c.engineer WHERE c.id = :id")
    Optional<Collision> findByIdFetchEngineer(@Param("id") int id);

    @Query("SELECT c FROM Collision c LEFT JOIN FETCH c.engineer ORDER BY c.id")
    List<Collision> findAllFetchEngineers();

    // engineer.getCollisions()
    List<Collision> findByEngineer(Engineer engineer);
}
