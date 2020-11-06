package com.ateam.paw_pals.model.api;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequest {
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;

}
