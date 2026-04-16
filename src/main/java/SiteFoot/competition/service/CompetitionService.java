package SiteFoot.competition.service;

import SiteFoot.competition.dto.CompetitionResponseApiDto;
import SiteFoot.competition.dto.CompetitionResponseItemApiDto;
import SiteFoot.competition.entity.Competition;
import SiteFoot.competition.repository.CompetitionRepository;
import SiteFoot.season.dto.SeasonReponseApiDto;
import SiteFoot.season.entity.Season;
import SiteFoot.season.repository.SeasonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final SeasonRepository seasonRepository;
    private final RestClient apiSportClient;


    public CompetitionService(CompetitionRepository competitionRepository, SeasonRepository seasonRepository, RestClient apiSportClient) {
        this.competitionRepository = competitionRepository;
        this.seasonRepository = seasonRepository;
        this.apiSportClient = apiSportClient;
    }

    @Transactional
    public void importCompetitionAndSeason(long comp){

        String path = "/leagues?id=" + comp;

        CompetitionResponseApiDto response = callApi(path);
        if (response == null || response.getResponse() == null || response.getResponse().isEmpty()) {
            throw new RuntimeException("Erreur lors de la récupération des données");
        }


        CompetitionResponseItemApiDto responseItems = response.getResponse().get(0);

        Long competitionId = responseItems.getLeague().getId();
        Competition competition = competitionRepository.findByApiFootballId(competitionId).orElseGet(Competition::new);
        competition.setApiFootballId(responseItems.getLeague().getId());
        competition.setName(responseItems.getLeague().getName());
        competition.setCountry(responseItems.getCountry().getName());
        competition.setLogo(responseItems.getLeague().getLogo());
        competition = competitionRepository.save(competition);


        for (SeasonReponseApiDto c : responseItems.getSeasons()) {

            Season seasonToBdd = seasonRepository.findByCompetitionIdAndYear(competition.getId(), c.getYear()).orElseGet(Season::new);
            seasonToBdd.setCompetition(competition);
            seasonToBdd.setYear(c.getYear());
            seasonToBdd.setCurrent(c.isCurrent());
            seasonToBdd.setStartDate(LocalDate.parse(c.getStart()));
            seasonToBdd.setEndDate(LocalDate.parse(c.getEnd()));
            seasonRepository.save(seasonToBdd);
        }

    }


    private CompetitionResponseApiDto callApi(String path) {

        try {
        CompetitionResponseApiDto reponse = apiSportClient.get()
                .uri(path)
                // exécute la requete HTTP et prepare la recupereation de la reponse
                .retrieve()
                //utilise Jacson ObjectMapper
                .body(CompetitionResponseApiDto.class);

            return reponse;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de La'appel API ou du parsing Json", e);
        }
    }


}


