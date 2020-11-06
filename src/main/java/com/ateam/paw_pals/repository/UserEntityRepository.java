package com.ateam.paw_pals.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ateam.paw_pals.model.entity.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    
	public Optional<UserEntity> findByEmail(String email);
	
	public Optional<UserEntity> findByEmailAndDeactivatedAt(String email, Date deactivatedAt);
	
	@Modifying
	@Transactional
    @Query(value = "UPDATE user SET deactivated_at = ?2 WHERE id = ?1", nativeQuery = true)
    public int setDeactivatedAt(Long userId, Date deactivatedAt);
	
}
