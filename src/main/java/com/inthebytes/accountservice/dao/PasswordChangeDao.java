package com.inthebytes.accountservice.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.data.passchange.PasswordChange;
import com.inthebytes.stacklunch.data.passchange.PasswordChangeRepository;
import com.inthebytes.stacklunch.data.user.User;

@Repository
public interface PasswordChangeDao extends PasswordChangeRepository {
	PasswordChange findByUser(User user);
	PasswordChange findByConfirmationToken(String confirmationToken);
	
	List<PasswordChange> findAllByCreatedTimeBefore(Timestamp latestDate);
}
