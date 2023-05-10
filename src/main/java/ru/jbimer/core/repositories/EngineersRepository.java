package ru.jbimer.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.jbimer.core.models.Engineer;

import java.util.List;
import java.util.Optional;

@Repository
public interface EngineersRepository extends JpaRepository<Engineer, Integer> {

    Optional<Engineer> findByTelegramUsername(String telegramUsername);

    Optional<Engineer> findByEmail(String email);

    Optional<Engineer> findByUsername(String username);

    @Query("SELECT e FROM Engineer e LEFT JOIN FETCH e.collisions WHERE e.id = :id")
    Optional<Engineer> findByIdFetchCollisions(@Param("id") int id);

    @Query("SELECT e FROM Engineer e join e.projects p where p.id = :project_id")
    List<Engineer> findAllOnProject(@Param("project_id") int id);
}
