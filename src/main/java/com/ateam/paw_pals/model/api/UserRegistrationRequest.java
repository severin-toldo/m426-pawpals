package com.ateam.paw_pals.model.api;


import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.ateam.paw_pals.model.entity.AddressEntity;
import com.ateam.paw_pals.model.entity.AnimalEntity;
import com.ateam.paw_pals.shared.util.CommonUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
	
	@NotNull
	@Email(regexp = CommonUtils.EMAIL_REGEXP, message = CommonUtils.EMAIL_REGEXP_MESSAGE)
    private String email;
	
    @NotNull
    private String password;
    
    @NotNull
    private AddressEntity address;
	
	private String firstname;
	private String lastname;
	private String phoneNumber;
	private String about;
	private List<AnimalEntity> animals;

}
