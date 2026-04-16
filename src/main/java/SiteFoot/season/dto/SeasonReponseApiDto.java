package SiteFoot.season.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeasonReponseApiDto {

    private Integer year;
    private String start;
    private String end;
    private boolean current;

}
