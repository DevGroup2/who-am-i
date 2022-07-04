package com.eleks.academy.whoami.core.impl;

import com.eleks.academy.whoami.core.SynchronousPlayer;
import com.eleks.academy.whoami.model.request.CharacterSuggestion;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersistentPlayer implements SynchronousPlayer {

	@EqualsAndHashCode.Include private final String name;

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
		this.character = suggestion.getCharacter();
	}

}
