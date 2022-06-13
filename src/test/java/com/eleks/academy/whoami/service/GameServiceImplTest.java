package com.eleks.academy.whoami.service;

import com.eleks.academy.whoami.model.request.NewGameRequest;
import com.eleks.academy.whoami.repository.GameRepository;
import com.eleks.academy.whoami.service.impl.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    private final NewGameRequest gameRequest = new NewGameRequest();

    @BeforeEach
    public void setMockMvc() {

        gameRequest.setMaxPlayers(4);
        gameRepository.findAllAvailable("Name");
    }

    @Test
    void findAvailableGamesTest() {
        final String player = "name";
        var gameLight = gameService.findAvailableGames(player);
        assertThat(gameService.findAvailableGames(player)).usingRecursiveComparison()
                .isEqualTo(gameLight);
        assertThat(gameRepository.findAllAvailable(null)).isEqualTo(gameLight);
    }



    @Test
    void createGameTest() {
    }

    @Test
    void enrollToGameTest() {
    }

    @Test
    void findByIdAndPlayerTest() {
    }

    @Test
    void suggestCharacterTest() {
    }

    @Test
    void startGameTest() {
    }

    @Test
    void askQuestionTest() {
    }

    @Test
    void findTurnInfoTest() {
    }

    @Test
    void submitGuessTest() {
    }

    @Test
    void answerQuestionTest() {
    }
}
