package com.eleks.academy.whoami.core;

import com.eleks.academy.whoami.model.request.CharacterSuggestion;
import com.eleks.academy.whoami.model.response.ChoosingCharacter;

import java.util.concurrent.CompletableFuture;

public interface SynchronousPlayer {

	String getName();

	String getCharacter();

	void setCharacter(CharacterSuggestion suggestion);

}
