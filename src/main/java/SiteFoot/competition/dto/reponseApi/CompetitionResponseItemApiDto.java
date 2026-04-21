package SiteFoot.competition.dto.reponseApi;

import SiteFoot.season.dto.SeasonReponseApiDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompetitionResponseItemApiDto {
    private LeagueResponseApiDto league;
    private CountryReponseApiDto country;
    private List<SeasonReponseApiDto> seasons;
}
