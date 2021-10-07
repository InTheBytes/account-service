package com.inthebytes.accountservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.data.user.User;
import com.inthebytes.stacklunch.data.user.UserRepository;

@Repository
public interface UserDao extends UserRepository {
	User findByEmailIgnoreCase(String email);
	User findByUsername(String username);
	User findByUserId(String userId);
	
	Page<User> findAll(Pageable pageable);
	Page<User> findByActive(Boolean active, Pageable pageable);
}
