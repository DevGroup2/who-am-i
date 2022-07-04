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

	private boolean suggested = false;
	private String character;

	public PersistentPlayer(String name) {
		this.name = Objects.requireNonNull(name);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getCharacter() {
		return character;
	}

	@Override
	public void setCharacter(CharacterSuggestion suggestion) {
		if (this.suggested) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Character has already been suggested!");
		} else {
			chooseCharacter(suggestion.getCharacter());
			this.suggested = true;
		}
	}
	private void chooseCharacter(String character) {
		this.character = character;
	}

}
