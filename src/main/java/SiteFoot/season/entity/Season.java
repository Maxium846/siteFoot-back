package SiteFoot.season.entity;
import SiteFoot.competition.entity.Competition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "season")
@Getter
@Setter
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean current;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", nullable = false  )
    private Competition competition;
}
