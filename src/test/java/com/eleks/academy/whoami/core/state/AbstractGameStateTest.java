package com.eleks.academy.whoami.core.state;

import com.eleks.academy.whoami.core.SynchronousPlayer;
import com.eleks.academy.whoami.core.impl.PersistentPlayer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractGameStateTest {

	@Test
	void findPlayerTest() {
		String player = "player";
		int maxPlayer = 4;
		final Map<String, SynchronousPlayer> players = new HashMap<>(maxPlayer);

		var state = new StateTest(1, maxPlayer, players);

		players.put(player, new PersistentPlayer(player));

		var findPlayer = state.findPlayer(player);
		var newPlayer = new PersistentPlayer(player);
		Optional<SynchronousPlayer> expectedPlayer = Optional.of(newPlayer);

		assertEquals(expectedPlayer, findPlayer);
	}

	@Test
	void getPlayersTest() {
		String player = "player";
		int maxPlayer = 4;
		final Map<String, SynchronousPlayer> players = new HashMap<>(maxPlayer);

		var state = new StateTest(1, maxPlayer, players);

		players.put(player, new PersistentPlayer(player));

		var playersMap = state.getPlayers();
		Map<String, SynchronousPlayer> expectedPlayers = new HashMap<>(maxPlayer);
		expectedPlayers.put(player, new PersistentPlayer(player));

		assertEquals(expectedPlayers, playersMap);
	}

	@Test
	void deletePlayer() {
		String player = "player";
		int maxPlayer = 4;
		final Map<String, SynchronousPlayer> players = new HashMap<>(maxPlayer);

		var state = new StateTest(1, maxPlayer, players);

		players.put(player, new PersistentPlayer(player));

		state.deletePlayer(player);

		assertTrue(players.isEmpty());
	}

	public class StateTest extends AbstractGameState {

		public StateTest(int playersInGame, int maxPlayers, Map<String, SynchronousPlayer> players) {
			super(playersInGame, maxPlayers, players);
		}

		@Override
		public GameState next() {
			return null;
		}
	}

}
