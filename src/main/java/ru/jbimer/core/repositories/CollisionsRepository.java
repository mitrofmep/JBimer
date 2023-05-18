package ru.jbimer.core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.models.Project;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CollisionsRepository extends JpaRepository<Collision, Integer> {

    @Query("SELECT c FROM Collision c LEFT JOIN c.projectBase p WHERE p = :project and (c.discipline1 = :discipline or c.discipline2 = :discipline)")
    Page<Collision> findByDisciplinesAndProject(@Param("discipline") String discipline,
                                                @Param("project") Project project,
                                                Pageable pageable);
    
    @Query("SELECT c FROM Collision c LEFT JOIN FETCH c.engineer WHERE c.id = :id")
    Optional<Collision> findByIdFetchEngineer(@Param("id") int id);

    @Query("SELECT c FROM Collision c LEFT JOIN FETCH c.engineer ORDER BY c.id")
    List<Collision> findAllFetchEngineers();

    List<Collision> findByEngineer(Engineer engineer);

    @Query("SELECT c FROM Collision c LEFT JOIN FETCH c.projectBase WHERE c.projectBase = :project and " +
            "(c.elementId1 =: elemId1 and c.elementId2 = :elemId2" +
            " or c.elementId1 = :elemId2 and c.elementId2 = :elemId1)")
    Optional<Collision> findByIdsForValidation(@Param("elemId1") String elemId1,
                                               @Param("elemId2") String elemId2,
                                               @Param("project") Project project);


    @Modifying
    @Query("update Collision c set c.status = 'Done' where c.projectBase = :project " +
            "and c.createdAt < :currDate and (c.discipline1 = :disc1 and c.discipline2 = :disc2 " +
            "or c.discipline1 = :disc2 and c.discipline2 = :disc1)")
    void updateStatus(@Param("currDate") Date date,
                      @Param("disc1") String disc1,
                      @Param("disc2") String disc2,
                      @Param("project") Project project);

    Page<Collision> findByProjectBase(Project project, Pageable pageable);

    List<Collision> findAllByProjectBase(Project project);
}
