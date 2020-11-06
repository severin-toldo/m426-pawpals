package com.ateam.paw_pals.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.paw_pals.model.entity.AnimalEntity;
import com.ateam.paw_pals.service.AnimalEntityService;
import com.ateam.paw_pals.service.CurrentSessionService;


@RestController
@RequestMapping("/api/animals")
public class AnimalRestService {
	
	@Autowired
    private AnimalEntityService animalEntityService;
	
	@Autowired
	private CurrentSessionService css;
	
	
 	@RequestMapping(method = RequestMethod.GET)
 	public List<AnimalEntity> getAnimalsByUser() {
 		return animalEntityService.getAnimalsByUser(css.getLoggedInUserEntity());
 	}
 	
 	@RequestMapping(method = RequestMethod.POST)
 	public AnimalEntity save(@RequestBody AnimalEntity ae) {
 		return animalEntityService.save(ae, css.getLoggedInUserEntity());
 	}

}
