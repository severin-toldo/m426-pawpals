package com.ateam.paw_pals.model.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEditRequest {
	private String firstname;
	private String lastname;
	private String phoneNumber;
}
