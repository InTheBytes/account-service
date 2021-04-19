package com.inthebytes.accountservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.accountservice.model.UserRole;

@Repository
public interface RoleDAO extends JpaRepository<UserRole, Long> {
	UserRole findRoleByRoleId(Integer id);
}