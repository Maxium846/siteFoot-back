package SiteFoot.season.repository;

import SiteFoot.season.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SeasonRepository extends JpaRepository<Season,Long> {

    Optional<Season> findByCompetitionIdAndYear(Long competitionId, Integer year);

    Optional<List<Season>> findByCompetitionId(Long competitionId);
    @Query("""
       SELECT DISTINCT ts.years
       FROM TeamSeason ts
       WHERE ts.competition.id = :competitionId
       ORDER BY ts.years DESC
       """)
    List<Integer> findDistinctYearsByCompetitionId(@Param("competitionId") int competitionId);
}
