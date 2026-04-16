package SiteFoot.season.repository;

import SiteFoot.season.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season,Long> {

    Optional<Season> findByCompetitionIdAndYear(Long competitionId, Integer year);
}
