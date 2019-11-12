package org.store.domain;

import io.swagger.annotations.ApiModelProperty;
import org.store.domain.enums.RoleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role implements Serializable{
	private static final long serialVersionUID = 890245234L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;

	@ApiModelProperty(notes = "Role type")
	@Column(name = "name", nullable = false,unique=true)
	@Enumerated(EnumType.STRING)
	private RoleType name;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserRole> userRoles = new HashSet<>();
	
	public Role(){}

	public Role(RoleType name){this.name = name;}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public RoleType getName() {
		return name;
	}

	public void setName(RoleType name) {
		this.name = name;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
}
