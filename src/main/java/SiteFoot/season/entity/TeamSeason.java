package SiteFoot.season.entity;

import SiteFoot.competition.entity.Competition;
import SiteFoot.team.entity.Teams;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TeamSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Teams team;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;
    private String nameTeam;
    private Integer years;
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;
}
