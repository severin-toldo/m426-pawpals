package com.ateam.paw_pals.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ateam.paw_pals.model.entity.RoleEntity;
import com.ateam.paw_pals.repository.RoleEntityRepository;

import java.util.List;


@Service
public class RoleEntityService {

	@Autowired
    private RoleEntityRepository roleEntityRepository;


    public RoleEntity getByName(String name) {
        return roleEntityRepository.findByName(name);
    }

    public List<RoleEntity> getRoles() {
        return roleEntityRepository.findAll();
    }
}
