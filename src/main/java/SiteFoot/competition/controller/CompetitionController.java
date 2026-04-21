package SiteFoot.competition.controller;

import SiteFoot.competition.dto.CompetitionDto;
import SiteFoot.competition.service.CompetitionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/all")
    public List<CompetitionDto> getAllCompetition(){

        return competitionService.getAllCompetition();
    }
}
