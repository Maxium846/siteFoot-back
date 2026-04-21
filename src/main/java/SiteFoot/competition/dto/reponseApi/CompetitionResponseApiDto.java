package SiteFoot.competition.dto.reponseApi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompetitionResponseApiDto {

    private List<CompetitionResponseItemApiDto> response;
}
