package com.ateam.paw_pals.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ateam.paw_pals.model.entity.UserEntity;

@Service
public class CurrentSessionService {
	
	@Autowired
	private UserEntityService userEntityService;
	
	public UserEntity getLoggedInUserEntity() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userEntityService.getActiveUserByEmail(authentication.getName());
	}
	
	public boolean isRequestorSameAsUser(Long userId) {
		UserEntity loggedInUserEntity = getLoggedInUserEntity();
		return loggedInUserEntity.getId().equals(userId);
	}
	
	public boolean isRequestorSameAsUser(String email) {
		UserEntity loggedInUserEntity = getLoggedInUserEntity();
		return StringUtils.equals(loggedInUserEntity.getEmail(), email);
	}
	
	public boolean isRequestorSameAsUserOrAdmin(Long userId) {
		UserEntity loggedInUserEntity = getLoggedInUserEntity();
		return loggedInUserEntity.getId().equals(userId) || userEntityService.isAdmin(loggedInUserEntity);
	}
	
	public boolean isRequestorSameAsUserOrAdmin(String email) {
		UserEntity loggedInUserEntity = getLoggedInUserEntity();
		return loggedInUserEntity.getEmail().equals(email) || userEntityService.isAdmin(loggedInUserEntity);
	}
	
}
