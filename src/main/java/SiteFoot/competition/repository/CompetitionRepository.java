package SiteFoot.competition.repository;

import SiteFoot.competition.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,Long> {

    Optional<Competition> findByApiFootballId(Long apiFootballId);
}
