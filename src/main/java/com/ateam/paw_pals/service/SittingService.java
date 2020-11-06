package com.ateam.paw_pals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ateam.paw_pals.model.entity.SitEntity;
import com.ateam.paw_pals.model.entity.UserEntity;
import com.ateam.paw_pals.repository.SitEntityRepository;

@Service
public class SittingService {
	
	@Autowired
	private SitEntityRepository sitEntityRepository;
	
	
	public List<SitEntity> getSits() {
		return sitEntityRepository.findBySitter(null);
	}
	
	public SitEntity createSit(SitEntity newSit) {
		return sitEntityRepository.save(newSit);
	}
	
	public SitEntity sit(UserEntity loggedInUser, Long sitId) {
		SitEntity sit = sitEntityRepository.findById(sitId).orElseThrow(() -> new IllegalArgumentException("Sit with id " + sitId + " does not exist!"));
		sit.setSitter(loggedInUser);
		
		return sitEntityRepository.save(sit);
	}

}
