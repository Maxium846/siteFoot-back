package SiteFoot.team.entity;

import SiteFoot.competition.entity.Competition;
import SiteFoot.season.entity.Season;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long apiFootballId;
    private LocalDate founded;
    private String logo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "competitionId")
    private Competition competition;
    private String nameStadium;
    private String city;
    private String surface;
    private String image;
    private Long capacity;
    private String currentCoach;



}
