package com.inthebytes.accountservice.dao;

import com.inthebytes.accountservice.entity.User;

import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDao extends JpaRepository<User, String> {
	User findByEmailIgnoreCase(String email);
	User findByUsername(String username);
	User findByUserId(String userId);
	
	Page<User> findAll(Pageable pageable);
	Page<User> findByActive(Boolean active, Pageable pageable);
}
