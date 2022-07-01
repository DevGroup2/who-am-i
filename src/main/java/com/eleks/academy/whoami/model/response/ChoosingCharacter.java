package com.eleks.academy.whoami.model.response;

import com.eleks.academy.whoami.core.SynchronousPlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoosingCharacter {

    private String character;

    public static ChoosingCharacter of(SynchronousPlayer player) {
        return ChoosingCharacter.builder()
                .character(player.getSuggestedCharacter())
                .build();
    }
}
