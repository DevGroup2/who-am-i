package com.eleks.academy.whoami.service;

import com.eleks.academy.whoami.core.SynchronousGame;
import com.eleks.academy.whoami.core.impl.PersistentGame;
import com.eleks.academy.whoami.core.impl.PersistentPlayer;
import com.eleks.academy.whoami.enums.GameStatus;
import com.eleks.academy.whoami.model.request.CharacterSuggestion;
import com.eleks.academy.whoami.model.request.NewGameRequest;
import com.eleks.academy.whoami.model.response.*;
import com.eleks.academy.whoami.repository.GameRepository;
import com.eleks.academy.whoami.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.eleks.academy.whoami.enums.GameStatus.WAITING_FOR_PLAYERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
	}

	@Test
	void findAvailableGamesTest () {
		final String player = "player";

		SynchronousGame synchronousGame = new PersistentGame(player, gameRequest.getMaxPlayers());
		when(gameRepository.findAllAvailable(player)).thenReturn(List.of(synchronousGame));

		assertThat(gameService.findAvailableGames(player))
				.usingRecursiveFieldByFieldElementComparatorOnFields("status")
				.containsOnly(GameLight.builder()
						.id("some id")
						.status(WAITING_FOR_PLAYERS)
						.build());
	}

	@Test
	void createGameTest() {
		final String idNaming = "id";
		final String player = "player";
		final GameStatus expectedGameStatus = WAITING_FOR_PLAYERS;

		SynchronousGame game = new PersistentGame(player, gameRequest.getMaxPlayers());

		when(gameRepository.save(any(SynchronousGame.class))).thenReturn(game);

		var gameDetails = gameService.createGame(player, gameRequest);

		var expectedGame = GameDetails.builder()
				.status(expectedGameStatus)
				.players(List.of(new PlayerWithState(new PersistentPlayer(player), PlayerState.READY)))
				.build();

		assertThat(gameDetails)
				.usingRecursiveComparison()
				.ignoringFields(idNaming)
				.isEqualTo(expectedGame);

		assertEquals(game.getId(), gameDetails.getId());
		assertEquals(game.getStatus(), gameDetails.getStatus());

		verify(gameRepository, times(1)).save(any(SynchronousGame.class));
	}

	@Test
	void findByIdAndPlayerTest() {
		final String player = "player";
		final GameStatus expectedGameStatus = WAITING_FOR_PLAYERS;

		SynchronousGame game = new PersistentGame(player, gameRequest.getMaxPlayers());
		final String id = game.getId();

		Optional<SynchronousGame> createdGame = Optional.of(game);

		when(gameRepository.findById(id)).thenReturn(createdGame);

		var foundGame = gameService.findByIdAndPlayer(id, player);
		var expectedGame = GameDetails.builder()
				.id(foundGame.get().getId())
				.status(expectedGameStatus)
				.players(List.of(new PlayerWithState(new PersistentPlayer(player), PlayerState.READY)))
				.build();
		Optional<GameDetails> expectedGameOp = Optional.of(expectedGame);

		assertEquals(foundGame, expectedGameOp);

		verify(gameRepository, times(1)).findById(id);
	}

	@Test
	void findByIdAndPlayerFailTest() {
		final String player = "player";
		final String fakeId = "12345";

		SynchronousGame game = new PersistentGame(player, gameRequest.getMaxPlayers());
		final String id = game.getId();

		Optional<SynchronousGame> createdGame = Optional.of(game);

		when(gameRepository.findById(id)).thenReturn(createdGame);

		var foundGame = gameService.findByIdAndPlayer(id, player);
		var fakeGame = gameService.findByIdAndPlayer(fakeId, player);

		assertFalse(fakeGame.isPresent());

		verify(gameRepository, times(1)).findById(id);
	}

	@Test
	void enrollToGameTest() {
		final String player = "player";
		final String newPlayer = "newPlayer";

		SynchronousGame game = new PersistentGame(player, gameRequest.getMaxPlayers());
		Optional<SynchronousGame> createdGame = Optional.of(game);
		final String id = game.getId();

		when(gameRepository.findById(id)).thenReturn(createdGame);

		var enrolledPlayer = gameService.enrollToGame(id, newPlayer);
		var expectedPlayer = new PersistentPlayer(newPlayer);

		assertEquals(enrolledPlayer, expectedPlayer);
	}

	@Test
	void suggestCharacterWhenGameIsNotFoundTest() {
		final String player = "Player";
		CharacterSuggestion suggestion = new CharacterSuggestion("Bet Monkey");

		SynchronousGame testedGame = new PersistentGame(player, gameRequest.getMaxPlayers());
		final String id = testedGame.getId();

		assertThrows(ResponseStatusException.class, () -> gameRepository.findById(id)
				.map(game -> game.findPlayer("some other player"))
				.ifPresentOrElse(p -> p.ifPresentOrElse(suggest -> suggest.setCharacter(suggestion.getCharacter()),
								() -> {
									throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't found a player");
								}
						),
						() -> {
							throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
						}
				));

	}
	@Test
	void suggestCharacterWhenPLayerIsNotFoundTest() {
		final String player = "Player";
		CharacterSuggestion suggestion = new CharacterSuggestion("Bet Monkey");

		final SynchronousGame testedGame = new PersistentGame(player, gameRequest.getMaxPlayers());
		final String id = testedGame.getId();

		assertThrows(ResponseStatusException.class, () -> gameRepository.findById(id)
				.flatMap(game -> game.findPlayer(player))
				.ifPresentOrElse(suggest -> suggest.setCharacter(suggestion.getCharacter()),
						() -> {
							throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't found a player");
						}
				));
	}

}
