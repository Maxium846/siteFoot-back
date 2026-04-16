package SiteFoot.team.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamResponseApiDto {

    private List<TeamResponseItemsApiDto> response;
}
