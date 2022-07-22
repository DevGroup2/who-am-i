package com.eleks.academy.whoami.core.state;

import com.eleks.academy.whoami.core.SynchronousPlayer;
import com.eleks.academy.whoami.core.Turn;
import com.eleks.academy.whoami.core.action.PlayerAction;
import com.eleks.academy.whoami.core.exception.GameException;
import com.eleks.academy.whoami.core.impl.TurnImpl;
import com.eleks.academy.whoami.enums.GameStatus;
import com.eleks.academy.whoami.enums.PlayerState;

import java.util.List;
import java.util.Map;

public final class ProcessingQuestion extends AbstractGameState {
	private Turn turn;

	public ProcessingQuestion(Map<String, SynchronousPlayer> players) {
		super(players.size(), players.size(), players);
		this.turn = new TurnImpl(players.values().stream().toList());
		updatePlayersState(this.getCurrentTurn(), players);
	}

	@Override
	public GameState next() {
		throw new GameException("Not implemented");
	}

	@Override
	public GameStatus getStatus() {
		return GameStatus.IN_PROGRESS;
	}

	@Override
	public List<PlayerAction> getCurrentTurn() {
		return this.turn.getCurrentTurn();
	}

	public void ask(String player, PlayerAction.Action question) {
		throw new GameException("Not implemented");
	}

	;

	public void answer(String player, PlayerAction answer) {
		throw new GameException("Not implemented");
	}

	;

	private void updatePlayersState(List<PlayerAction> playerActions, Map<String, SynchronousPlayer> players) {
		String askingPlayer = playerActions
				.stream()
				.filter(action -> action.getAction().equals(PlayerAction.Action.QUESTION))
				.map(PlayerAction::getPlayer)
				.findFirst()
				.orElseThrow(() -> new GameException("Cannot find asking player"));

		for (var player : players.entrySet()) {
			if (player.getValue().getName().equals(askingPlayer)) {
				player.getValue().setPlayerState(PlayerState.ASKING);
			} else {
				player.getValue().setPlayerState(PlayerState.WAITING_FOR_QUESTION);
			}
		}
	}

}
