package SiteFoot.team.mapper;

import SiteFoot.season.entity.TeamSeason;
import SiteFoot.team.dto.TeamsListDto;

public class TeamMapper {

    public static TeamsListDto toDto(TeamSeason teams) {

        TeamsListDto dto = new TeamsListDto();
        dto.setId(teams.getId());
        dto.setName(teams.getNameTeam());

        return dto;
    }

}
