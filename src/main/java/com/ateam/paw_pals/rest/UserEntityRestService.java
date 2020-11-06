package com.ateam.paw_pals.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ateam.paw_pals.model.api.FileUploadResponse;
import com.ateam.paw_pals.model.api.UserEditRequest;
import com.ateam.paw_pals.model.api.UserRegistrationRequest;
import com.ateam.paw_pals.model.entity.UserEntity;
import com.ateam.paw_pals.service.CurrentSessionService;
import com.ateam.paw_pals.service.UserEntityService;
import com.ateam.paw_pals.shared.util.CommonUtils;

@RestController
@RequestMapping("/api/users")
public class UserEntityRestService {

	@Autowired
    private UserEntityService userEntityService;
	
	@Autowired
	private CurrentSessionService css;
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
 	@RequestMapping(method = RequestMethod.GET)
 	public List<UserEntity> getUsers() {
 		return userEntityService.getUsers();
 	}
	
    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public UserEntity register(@RequestBody @Validated UserRegistrationRequest urr) {
    	return userEntityService.register(urr);
    }
 	 
 	@RequestMapping(value = "id/{userId}", method = RequestMethod.GET)
 	public UserEntity getById(@PathVariable Long userId) {
 		CommonUtils.falseThenThrow(css.isRequestorSameAsUserOrAdmin(userId), new IllegalArgumentException("Requestor is not the same or has no permission"));
 		return userEntityService.getById(userId);
 	}
 	
 	@RequestMapping(value = "email/{email}", method = RequestMethod.GET)
 	public UserEntity getByEmail(@PathVariable String email) {
 		CommonUtils.falseThenThrow(css.isRequestorSameAsUserOrAdmin(email), new IllegalArgumentException("Requestor is not the same or has no permission"));
 		return userEntityService.getByEmail(email);
 	}
 	
 	@RequestMapping(value = "activate/{userId}", method = RequestMethod.POST)
 	public UserEntity activateUser(@PathVariable Long userId) {
 		CommonUtils.falseThenThrow(css.isRequestorSameAsUserOrAdmin(userId), new IllegalArgumentException("Requestor is not the same or has no permission"));
 		return userEntityService.activateUser(userId);
 	}
 	
 	@RequestMapping(value = "deactivate/{userId}", method = RequestMethod.POST)
 	public UserEntity deactivateUser(@PathVariable Long userId) {
 		CommonUtils.falseThenThrow(css.isRequestorSameAsUserOrAdmin(userId), new IllegalArgumentException("Requestor is not the same or has no permission"));
 		return userEntityService.deactivateUser(userId);
 	}
 	
 	@RequestMapping(value = "edit", method = RequestMethod.POST)
 	public UserEntity editUser(@RequestBody @NotNull @Validated UserEditRequest uer) {
 		return userEntityService.editUser(css.getLoggedInUserEntity(), uer);
 	}
 	
 	@RequestMapping(value = "profile-picture", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadProfilePicture(HttpServletRequest request) throws Exception {
		return userEntityService.downloadProfilePicture(css.getLoggedInUserEntity(), request);
    }
	
	@RequestMapping(value = "profile-picture/upload", method = RequestMethod.POST)
    public FileUploadResponse uploadProfilePicture(@RequestParam("file") MultipartFile file) throws Exception {
		return userEntityService.uploadProfilePicture(css.getLoggedInUserEntity(), file);
    }
}
