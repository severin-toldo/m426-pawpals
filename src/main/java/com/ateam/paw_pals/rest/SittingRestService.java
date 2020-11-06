package com.ateam.paw_pals.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.paw_pals.model.entity.SitEntity;
import com.ateam.paw_pals.service.CurrentSessionService;
import com.ateam.paw_pals.service.SittingService;

@RestController
@RequestMapping("/api/sits")
public class SittingRestService {

	@Autowired
	private SittingService sittingService;

	@Autowired
	private CurrentSessionService css;

	@RequestMapping(method = RequestMethod.GET)
	public List<SitEntity> getSits() {
		return sittingService.getSits();
	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public SitEntity createSit(@RequestBody @Validated SitEntity newSit) {
		return sittingService.createSit(newSit);
	}

	@RequestMapping(value = "sit/{sitId}", method = RequestMethod.POST)
	public SitEntity sit(@PathVariable Long sitId) {
		return sittingService.sit(css.getLoggedInUserEntity(), sitId);
	}

}
