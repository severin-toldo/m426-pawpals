package com.ateam.paw_pals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.paw_pals.model.entity.AnimalEntity;
import com.ateam.paw_pals.model.entity.UserEntity;

public interface AnimalEntityRepository extends JpaRepository<AnimalEntity, Long> {
	
	public List<AnimalEntity> findAllByUser(UserEntity ue);
    
}
