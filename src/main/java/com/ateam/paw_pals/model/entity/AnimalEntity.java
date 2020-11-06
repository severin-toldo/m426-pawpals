package com.ateam.paw_pals.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "animal")
public class AnimalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "age", nullable = true)
	private Integer age;

	@Column(name = "gender", nullable = true)
	private String gender;

	@Column(name = "breed", nullable = true)
	private String breed;

	@Column(name = "weight", nullable = true)
	private Double weight;

	@Column(name = "height", nullable = true)
	private Double height;

	@Column(name = "fears", nullable = true)
	private String fears;

	@Column(name = "about", nullable = true)
	private String about;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id_fk")
	private UserEntity user;

}
