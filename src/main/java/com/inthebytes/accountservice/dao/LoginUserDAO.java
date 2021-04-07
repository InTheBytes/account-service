package com.inthebytes.accountservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.accountservice.model.LoginUser;

@Repository
public interface LoginUserDAO extends JpaRepository<LoginUser, Long> {
	LoginUser findByUsername(String username);
}
