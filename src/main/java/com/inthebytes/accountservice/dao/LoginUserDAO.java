package com.inthebytes.accountservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inthebytes.accountservice.model.LoginUser;

@Repository
public interface LoginUserDAO extends JpaRepository<LoginUser, Long> {

	@Query ("SELECT u FROM user u WHERE u.username = ?1")
	LoginUser findUserByUsername(String username);
}
