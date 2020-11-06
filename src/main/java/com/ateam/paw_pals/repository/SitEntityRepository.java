package com.ateam.paw_pals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ateam.paw_pals.model.entity.SitEntity;
import com.ateam.paw_pals.model.entity.UserEntity;

@Repository
public interface SitEntityRepository extends JpaRepository<SitEntity, Long> {
	
	public List<SitEntity> findBySitter(UserEntity sitter);
    
}
