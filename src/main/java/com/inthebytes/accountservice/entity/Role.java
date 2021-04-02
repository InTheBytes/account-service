package com.inthebytes.accountservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class Role {
	private Long roleId;
	private String name;
	private Collection<User> usersByRoleId;

	@Id
	@Column(name = "role_id", nullable = false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Basic
	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		if (roleId != null ? !roleId.equals(role.roleId) : role.roleId != null) return false;
		if (name != null ? !name.equals(role.name) : role.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = roleId != null ? roleId.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "roleByUserRole")
	public Collection<User> getUsersByRoleId() {
		return usersByRoleId;
	}

	public void setUsersByRoleId(Collection<User> usersByRoleId) {
		this.usersByRoleId = usersByRoleId;
	}
}
