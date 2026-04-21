package SiteFoot.season.service;

import SiteFoot.season.repository.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public List<Integer> getListSeasonByCompetition(int competitionId){

        return seasonRepository.findDistinctYearsByCompetitionId(competitionId);
    }
}
