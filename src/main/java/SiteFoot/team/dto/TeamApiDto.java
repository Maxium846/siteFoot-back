package SiteFoot.team.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TeamApiDto {

    private Long id;
    private String name;
    private  String country;
    private LocalDate founded;
    private String logo;
}
