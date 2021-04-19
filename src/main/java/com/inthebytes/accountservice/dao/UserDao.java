package com.inthebytes.accountservice.dao;

import com.inthebytes.accountservice.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	User findByEmailIgnoreCase(String email);
	User findByUsername(String username);
}
