package com.eleks.academy.whoami.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewGameRequest {

	@Min(value = 4, message = "maxPlayers value must be equals 4")
	@Max(value = 4, message = "maxPlayers value must be equals 4")
	@NotNull(message = "maxPlayers must not be null")
	private Integer maxPlayers;

}
