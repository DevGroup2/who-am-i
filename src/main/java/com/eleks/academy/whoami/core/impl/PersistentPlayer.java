package com.eleks.academy.whoami.core.impl;

import com.eleks.academy.whoami.core.SynchronousPlayer;
import com.eleks.academy.whoami.core.exception.GameException;
import com.eleks.academy.whoami.model.request.CharacterSuggestion;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersistentPlayer implements SynchronousPlayer {

	@EqualsAndHashCode.Include private final String name;

	private String assignedCharacter;
	private boolean assigned = false;
	private boolean suggested = false;
	private String suggestedCharacter;

	public PersistentPlayer(String name) {
		this.name = Objects.requireNonNull(name);
	}

	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String getSuggestedCharacter() {
		return suggestedCharacter;
	}
	@Override
	public String getCharacter() {
		return assignedCharacter;
	}
	@Override
	public void setCharacter(String character) {
		if (!assigned) {
			suggestedCharacter = character;
			assigned = true;
		} else {
			throw new GameException("Something going wrong when trying to assign a character to a player");
		}
	}
	private void chooseCharacter(String character) {
		this.suggestedCharacter = character;
	}
	@Override
	public void setSuggestedCharacter(CharacterSuggestion suggestion) {
		if (this.suggested){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Character has already been suggested!");
		}
		chooseCharacter(suggestion.getCharacter());
	}


}
