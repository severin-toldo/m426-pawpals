package com.ateam.paw_pals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.paw_pals.model.entity.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
	
    public RoleEntity findByName(String name);
    
}
