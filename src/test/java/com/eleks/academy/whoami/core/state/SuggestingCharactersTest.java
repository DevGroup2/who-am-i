package com.eleks.academy.whoami.core.state;

import com.eleks.academy.whoami.core.SynchronousPlayer;
import com.eleks.academy.whoami.core.impl.GameCharacter;
import com.eleks.academy.whoami.core.impl.PersistentPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class SuggestingCharactersTest {

    @InjectMocks
    SuggestingCharacters suggestingCharacters;

    @BeforeEach
    public void setMockMvc(){
        SynchronousPlayer synchronousPlayer = new PersistentPlayer("player");

    }

    @Test
    public void suggestCharacterTest(){
        final String player = "Player";
        final String character = "Mandalorian";
        final List<GameCharacter> characters = new ArrayList<>();

        final Map<String, List<GameCharacter>> suggestedCharacters;
        this.suggestingCharacters.findPlayer(player).stream();





    }


}
