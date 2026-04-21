package SiteFoot.season.controller;

import SiteFoot.season.service.SeasonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
public class SeasonController {

    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping("/{competitionId}")
    public List<Integer> getListSeasonByCompetitionId(@PathVariable int competitionId){
        return seasonService.getListSeasonByCompetition(competitionId);
    }
}
