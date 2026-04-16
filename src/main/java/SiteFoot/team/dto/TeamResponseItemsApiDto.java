package SiteFoot.team.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamResponseItemsApiDto {

    private TeamApiDto team;
    private VenueApiDto venue;

}
