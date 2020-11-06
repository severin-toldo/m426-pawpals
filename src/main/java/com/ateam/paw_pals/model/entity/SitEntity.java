package com.ateam.paw_pals.model.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sit")
public class SitEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@Column(name = "start_date")
	private Date startDate;
	
	@NotNull
	@Column(name = "end_date")
	private Date endDate;
	
	@NotNull
	@Column(name = "activities")
	private String activities;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="animal_id_fk")
	private AnimalEntity animal;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="pet_owner_id_fk")
	private UserEntity petOwner;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="sitter_id_fk")
	private UserEntity sitter;

}
