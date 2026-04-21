package SiteFoot.competittionTest;

import SiteFoot.competition.dto.reponseApi.CompetitionResponseApiDto;
import SiteFoot.competition.dto.reponseApi.CompetitionResponseItemApiDto;
import SiteFoot.competition.dto.reponseApi.CountryReponseApiDto;
import SiteFoot.competition.dto.reponseApi.LeagueResponseApiDto;
import SiteFoot.competition.entity.Competition;
import SiteFoot.competition.repository.CompetitionRepository;
import SiteFoot.competition.service.CompetitionService;
import SiteFoot.season.dto.SeasonReponseApiDto;
import SiteFoot.season.entity.Season;
import SiteFoot.season.repository.SeasonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



    class CompetitionServiceTest {

        private CompetitionRepository competitionRepository;
        private SeasonRepository seasonRepository;
        private RestClient apiSportClient;

        @SuppressWarnings("rawtypes")
        //évite les warning java lié aux types génériques bruts

        private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

        @SuppressWarnings("rawtypes")
        private RestClient.RequestHeadersSpec requestHeadersSpec;
        private RestClient.ResponseSpec responseSpec;

        private CompetitionService competitionService;

        @BeforeEach
        void setUp() {
            competitionRepository = mock(CompetitionRepository.class);
            seasonRepository = mock(SeasonRepository.class);
            apiSportClient = mock(RestClient.class);

            requestHeadersUriSpec = mock(RestClient.RequestHeadersUriSpec.class);
            requestHeadersSpec = mock(RestClient.RequestHeadersSpec.class);
            responseSpec = mock(RestClient.ResponseSpec.class);

            competitionService = new CompetitionService(
                    competitionRepository,
                    seasonRepository,
                    apiSportClient
            );
        }

@Test
void shouldImportCompetitionAndSeasons() {
            LeagueResponseApiDto leagueDto = new LeagueResponseApiDto();
            leagueDto.setId(39L);
            leagueDto.setName("Premier League");
            leagueDto.setLogo("logo.png");

            CountryReponseApiDto countryDto = new CountryReponseApiDto();
            countryDto.setName("England");

            SeasonReponseApiDto season1 = new SeasonReponseApiDto();
            season1.setYear(2023);
            season1.setCurrent(false);
            season1.setStart("2023-08-11");
            season1.setEnd("2024-05-19");

            SeasonReponseApiDto season2 = new SeasonReponseApiDto();
            season2.setYear(2024);
            season2.setCurrent(true);
            season2.setStart("2024-08-16");
            season2.setEnd("2025-05-25");

            CompetitionResponseItemApiDto item = new CompetitionResponseItemApiDto();
            item.setLeague(leagueDto);
            item.setCountry(countryDto);
            item.setSeasons(List.of(season1, season2));

            CompetitionResponseApiDto responseDto = new CompetitionResponseApiDto();
            responseDto.setResponse(List.of(item));

            when(apiSportClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri("/leagues?id=39")).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.body(CompetitionResponseApiDto.class)).thenReturn(responseDto);

            when(competitionRepository.findByApiFootballId(39L)).thenReturn(Optional.empty());

            Competition savedCompetition = new Competition();
            savedCompetition.setId(1L);
            savedCompetition.setApiFootballId(39L);
            savedCompetition.setName("Premier League");
            savedCompetition.setCountry("England");
            savedCompetition.setLogo("logo.png");

            when(competitionRepository.save(any(Competition.class))).thenReturn(savedCompetition);
            when(seasonRepository.findByCompetitionIdAndYear(1L, 2023)).thenReturn(Optional.empty());
            when(seasonRepository.findByCompetitionIdAndYear(1L, 2024)).thenReturn(Optional.empty());

            competitionService.importCompetitionAndSeason(39L);

            ArgumentCaptor<Competition> competitionCaptor = ArgumentCaptor.forClass(Competition.class);
            verify(competitionRepository).save(competitionCaptor.capture());

            Competition capturedCompetition = competitionCaptor.getValue();
            assertEquals(39L, capturedCompetition.getApiFootballId());
            assertEquals("Premier League", capturedCompetition.getName());
            assertEquals("England", capturedCompetition.getCountry());
            assertEquals("logo.png", capturedCompetition.getLogo());

            ArgumentCaptor<Season> seasonCaptor = ArgumentCaptor.forClass(Season.class);
            verify(seasonRepository, times(2)).save(seasonCaptor.capture());

            List<Season> savedSeasons = seasonCaptor.getAllValues();
            assertEquals(2, savedSeasons.size());

            Season firstSeason = savedSeasons.get(0);
            assertNotNull(firstSeason.getCompetition());
            assertEquals(2023, firstSeason.getYear());
            assertEquals(false, firstSeason.isCurrent());

            Season secondSeason = savedSeasons.get(1);
            assertEquals(2024, secondSeason.getYear());
            assertEquals(true, secondSeason.isCurrent());
        }
    }

