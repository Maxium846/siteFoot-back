package SiteFoot.team.controller;
import SiteFoot.team.dto.TeamsListDto;
import SiteFoot.team.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {


    private final TeamService teamService;


    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/generateClub/{competitionId}")
    public void importClubBySeasons (@PathVariable Long competitionId ){

        teamService.importTeamBySeason(competitionId);

    }
    @GetMapping("/{competitionId}/{years}")
    public List<TeamsListDto> getListClubById(@PathVariable int competitionId, @PathVariable int years){

        return teamService.getListTeams(competitionId,years);
    }

}
