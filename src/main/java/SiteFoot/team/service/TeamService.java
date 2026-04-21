package SiteFoot.team.service;

import SiteFoot.competition.entity.Competition;
import SiteFoot.competition.repository.CompetitionRepository;
import SiteFoot.season.entity.Season;
import SiteFoot.season.entity.TeamSeason;
import SiteFoot.season.repository.SeasonRepository;
import SiteFoot.season.repository.TeamSeasonRepository;
import SiteFoot.team.dto.TeamResponseApiDto;
import SiteFoot.team.dto.TeamResponseItemsApiDto;
import SiteFoot.team.dto.TeamsListDto;
import SiteFoot.team.entity.Teams;
import SiteFoot.team.mapper.TeamMapper;
import SiteFoot.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final CompetitionRepository competitionRepository;
    private final SeasonRepository seasonRepository;
    private final TeamSeasonRepository teamSeasonRepository;
    private final RestClient apiSportClient;


    public TeamService(TeamRepository teamRepository, CompetitionRepository competitionRepository, SeasonRepository seasonRepository, TeamSeasonRepository teamSeasonRepository, RestClient apiSportClient) {
        this.teamRepository = teamRepository;
        this.competitionRepository = competitionRepository;
        this.seasonRepository = seasonRepository;
        this.teamSeasonRepository = teamSeasonRepository;
        this.apiSportClient = apiSportClient;
    }


    public List<TeamsListDto> getListTeams (int competitionId, int years){

        return teamSeasonRepository.findAllByCompetitionIdAndYears(competitionId,years).stream().map(TeamMapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public void importTeamBySeason(Long competitionId) {
        Competition competition = competitionRepository.findByApiFootballId(competitionId).orElseThrow();
        List<Season> seasonList = seasonRepository.findByCompetitionId(competition.getId()).orElseThrow();

        for (Season s : seasonList) {

            String path = "/teams?league=" + competitionId + "&season=" + s.getYear();
            TeamResponseApiDto response = callApi(path);

            if(response==null || response.getResponse()==null || response.getResponse().isEmpty()){
                throw new RuntimeException("Erreur lors de la récupération des données");
            }

            for(TeamResponseItemsApiDto teamResponseItemsApiDto : response.getResponse()) {


                Long teamId = teamResponseItemsApiDto.getTeam().getId();

                Teams team = teamRepository.findByApiFootballId(teamId).orElseGet(Teams::new);
                team.setApiFootballId(teamResponseItemsApiDto.getTeam().getId());
                team.setName(teamResponseItemsApiDto.getTeam().getName());
                team.setFounded(teamResponseItemsApiDto.getTeam().getFounded());
                team.setLogo(teamResponseItemsApiDto.getTeam().getLogo());
                team.setNameStadium(teamResponseItemsApiDto.getVenue().getName());
                team.setCapacity(teamResponseItemsApiDto.getVenue().getCapacity());
                team.setImage(teamResponseItemsApiDto.getVenue().getImage());
                team.setCity(teamResponseItemsApiDto.getVenue().getCity());
                team.setSurface(teamResponseItemsApiDto.getVenue().getSurface());

                teamRepository.save(team);


                TeamSeason teamSeason = teamSeasonRepository.findByTeamAndSeason(team,s).orElseGet(TeamSeason ::new);
                teamSeason.setSeason(s);
                teamSeason.setCompetition(s.getCompetition());
                teamSeason.setTeam(team);
                teamSeason.setNameTeam(team.getName());
                teamSeason.setYears(s.getYear());

                teamSeasonRepository.save(teamSeason);

            }
        }

    }
    public TeamResponseApiDto callApi(String path) {

        try {
            return apiSportClient.get()
                    .uri(path)
                    .retrieve()
                    .body(TeamResponseApiDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'appel de l'api");
        }

    }
}
