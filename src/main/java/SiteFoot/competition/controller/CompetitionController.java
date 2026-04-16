package SiteFoot.competition.controller;

import SiteFoot.competition.service.CompetitionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping ("/api/league")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping("/generateLeague/{leagueId}")
    public void importLeague (@PathVariable long leagueId) throws Exception {

         competitionService.importCompetitionAndSeason(leagueId);

    }
}
