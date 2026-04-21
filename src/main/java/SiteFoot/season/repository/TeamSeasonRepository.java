package SiteFoot.season.repository;

import SiteFoot.season.entity.Season;
import SiteFoot.season.entity.TeamSeason;
import SiteFoot.team.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamSeasonRepository extends JpaRepository<TeamSeason,Long> {

    Optional<TeamSeason> findByTeamAndSeason(Teams teamId, Season seasonId);

    List<TeamSeason> findAllByCompetitionIdAndYears(int competitionId , int years);






}
