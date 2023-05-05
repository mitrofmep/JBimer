package ru.jbimer.core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollisionsRepository extends JpaRepository<Collision, Integer> {


    Page<Collision> findByDiscipline1ContainingIgnoreCaseOrDiscipline2ContainingIgnoreCaseOrderById(String keyword1, String keyword2, Pageable pageable);

    @Query("SELECT c FROM Collision c LEFT JOIN FETCH c.engineer WHERE c.id = :id")
    Optional<Collision> findByIdFetchEngineer(@Param("id") int id);

    @Query("SELECT c FROM Collision c LEFT JOIN FETCH c.engineer ORDER BY c.id")
    List<Collision> findAllFetchEngineers();

    // engineer.getCollisions()
    List<Collision> findByEngineer(Engineer engineer);
}
