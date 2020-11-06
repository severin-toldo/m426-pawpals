package com.ateam.paw_pals.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import com.ateam.paw_pals.shared.util.CommonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
    
	@NotNull
    @Column(name = "email", nullable = false, unique = true)
    @Email(regexp = CommonUtils.EMAIL_REGEXP, message = CommonUtils.EMAIL_REGEXP_MESSAGE)
    private String email;

	@JsonIgnore
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "deactivated_at", nullable = true)
	private Date deactivatedAt;
	
	@Column(name = "about", nullable = true)
	private String about;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> securityRoles = new ArrayList<>();
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="address_id_fk")
	private AddressEntity address;
    
}
