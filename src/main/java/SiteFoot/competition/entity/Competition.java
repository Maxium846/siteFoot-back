package SiteFoot.competition.entity;

import SiteFoot.season.entity.Season;
import SiteFoot.team.entity.Teams;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String logo;
    private String country;
    @OneToMany(mappedBy = "competition")
    private List<Season> seasons = new ArrayList<>();
    private Long apiFootballId;
    @OneToMany(mappedBy = "competition")
    private List<Teams> teams;




}
