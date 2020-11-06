package com.ateam.paw_pals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ateam.paw_pals.model.entity.AnimalEntity;
import com.ateam.paw_pals.model.entity.UserEntity;
import com.ateam.paw_pals.repository.AnimalEntityRepository;

@Service
public class AnimalEntityService {
	
	@Autowired
	private AnimalEntityRepository animalEntityRepository;
	
	
	public List<AnimalEntity> getAnimalsByUser(UserEntity ue) {
		return animalEntityRepository.findAllByUser(ue);
	}
	
	public AnimalEntity save(AnimalEntity ae, UserEntity ue) {
		ae.setUser(ue);
		return animalEntityRepository.save(ae);
	}

}
