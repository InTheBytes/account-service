package com.inthebytes.accountservice.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inthebytes.accountservice.model.UserAccount;

@Repository
public interface UserAccountDAO extends JpaRepository<UserAccount, Long> {
	List<UserAccount> findAll();
}
