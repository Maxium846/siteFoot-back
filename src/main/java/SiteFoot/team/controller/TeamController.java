package SiteFoot.team.controller;

import SiteFoot.team.service.TeamService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
public class TeamController {


    private final TeamService teamService;


    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/generateClub/{competitionId}")
    public void importClubBySeasons (@PathVariable Long competitionId ){

        teamService.importTeamBySaison(competitionId);

    }
}
