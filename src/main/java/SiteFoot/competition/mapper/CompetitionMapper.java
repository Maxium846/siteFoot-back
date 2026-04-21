package SiteFoot.competition.mapper;

import SiteFoot.competition.dto.CompetitionDto;
import SiteFoot.competition.entity.Competition;

public class CompetitionMapper {

    public static CompetitionDto toDto(Competition comp) {

        CompetitionDto dto = new CompetitionDto();

        dto.setId(comp.getId());
        dto.setName(comp.getName());
        dto.setCountry(comp.getCountry());
        return dto;
    }
}
