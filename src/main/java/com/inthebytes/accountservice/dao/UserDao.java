package com.inthebytes.accountservice.dao;

import com.inthebytes.accountservice.entity.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	User findByEmailIgnoreCase(String email);
	User findByUserId(Long userId);
	List<User> findAll();
	User findByUsername(String username);
	User findByUserId(Long userId);
}
