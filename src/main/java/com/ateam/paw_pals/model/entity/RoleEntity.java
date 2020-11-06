package com.ateam.paw_pals.model.entity;

import javax.persistence.*;

import com.ateam.paw_pals.model.SecurityRoleName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "security_role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 256)
    private SecurityRoleName name;
    
}
