package com.ateam.paw_pals.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.ateam.paw_pals.model.SecurityRoleName;
import com.ateam.paw_pals.model.api.FileUploadResponse;
import com.ateam.paw_pals.model.api.UserEditRequest;
import com.ateam.paw_pals.model.api.UserRegistrationRequest;
import com.ateam.paw_pals.model.entity.UserEntity;
import com.ateam.paw_pals.repository.UserEntityRepository;

@Service
public class UserEntityService {

	@Autowired
    private UserEntityRepository repository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private AnimalEntityService animalEntityService;

    
    public UserEntity getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));
	}
	
	public UserEntity getByEmail(String email) {
		return repository.findByEmail(StringUtils.trim(email)).orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found!"));
	}
	
	public UserEntity getActiveUserByEmail(String email) {
		return getByEmailAndDeactivatedAt(email, null);
	}
	
	public List<UserEntity> getUsers() {
		return repository.findAll();
	}
	
	public UserEntity activateUser(Long userId) {
		return setDeactivatedAt(userId, null);
	}
	
	public UserEntity deactivateUser(Long userId) {
		return setDeactivatedAt(userId, new Date());	
	}
    
    public UserEntity register(@Validated UserRegistrationRequest urr) {
    	UserEntity ue = new UserEntity();
    	ue.setFirstname(urr.getFirstname());
    	ue.setLastname(urr.getLastname());
    	ue.setPhoneNumber(urr.getPhoneNumber());
    	ue.setEmail(urr.getEmail().trim());
    	ue.setPassword(passwordEncoder.encode(urr.getPassword()));
    	ue.setAbout(urr.getAbout());
    	ue.setAddress(urr.getAddress());
    	
    	UserEntity savedUser = saveUser(ue);

    	urr.getAnimals().forEach(a -> {
    		animalEntityService.save(a, savedUser);
    	});
    	
        return savedUser;
    }
    
    public UserDetails getUserDetailsByUserName(String email) {
        UserEntity ue = getActiveUserByEmail(email);
        
        Set<SimpleGrantedAuthority> authorities = ue.getSecurityRoles()
        		.stream()
        		.map(r -> new SimpleGrantedAuthority(r.getName().name()))
        		.collect(Collectors.toSet());
        
        return new User(ue.getEmail(), ue.getPassword(), authorities);
    }
    
    private UserEntity saveUser(UserEntity ue) {
        return repository.saveAndFlush(ue);
    }
    
    public UserEntity editUser(UserEntity loggedInUser, @NotNull @Validated UserEditRequest uer) {
    	loggedInUser.setFirstname(uer.getFirstname());
    	loggedInUser.setLastname(uer.getLastname());
    	loggedInUser.setPhoneNumber(uer.getPhoneNumber());
		repository.save(loggedInUser);
		
		return getById(loggedInUser.getId());
	}
    
    public boolean isAdmin(UserEntity ue) {
		return ue.getSecurityRoles()
				.stream()
				.anyMatch(r -> r.getName() == SecurityRoleName.ADMIN);
    }
    
    public FileUploadResponse uploadProfilePicture(UserEntity loggedInUser, MultipartFile file) throws Exception {
    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    	
    	if (!StringUtils.equals(extension, "png")) {
    		throw new IllegalArgumentException("Only png are allowed!");
    	}
    	
		return fileService.uploadFile(file, getProfilePictureFileName(loggedInUser));
    }
    
    public ResponseEntity<Resource> downloadProfilePicture(UserEntity loggedInUser, HttpServletRequest request) throws Exception {
		return fileService.downloadFile(getProfilePictureFileName(loggedInUser), request);
    }
	
	private UserEntity getByEmailAndDeactivatedAt(String email, Date deactivatedAt) {
		return repository.findByEmailAndDeactivatedAt(StringUtils.trim(email), deactivatedAt).orElseThrow(() -> new EntityNotFoundException("Active User with email " + email + " not found!"));
	}
	
	private UserEntity setDeactivatedAt(Long userId, Date deactivatedAt) {
		int changedRows = repository.setDeactivatedAt(userId, deactivatedAt);
		
		if (changedRows == 0) {
			throw new IllegalStateException("Could not set deactivated_at on user " + userId);
		}
		
		return getById(userId);
	}
	
	private String getProfilePictureFileName(UserEntity loggedInUser) {
		return "profile_picture_" + loggedInUser.getId() + ".png";
	}
 }
