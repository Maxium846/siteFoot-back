package SiteFoot.team.repository;

import SiteFoot.team.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Teams,Long> {

     Optional<Teams> findByApiFootballId(Long apiFootballId);

}
