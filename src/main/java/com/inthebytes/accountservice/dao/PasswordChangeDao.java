package com.inthebytes.accountservice.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inthebytes.accountservice.entity.PasswordChange;
import com.inthebytes.accountservice.entity.User;

@Repository
public interface PasswordChangeDao extends JpaRepository<PasswordChange, String> {
	PasswordChange findByUser(User user);
	PasswordChange findByConfirmationToken(String confirmationToken);
	
	List<PasswordChange> findAllByCreatedTimeBefore(Timestamp latestDate);
}
