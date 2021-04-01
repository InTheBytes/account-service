package com.inthebytes.accountservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inthebytes.accountservice.model.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {

	@Query ("SELECT r FROM role r WHERE r.role_id = ?1")
	Role findRoleById(Integer id);
}