package com.inthebytes.accountservice.dao;

import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.data.confirmation.Confirmation;
import com.inthebytes.stacklunch.data.confirmation.ConfirmationRepository;
import com.inthebytes.stacklunch.data.user.User;

@Repository
public interface ConfirmationDao extends ConfirmationRepository {
	Confirmation findByConfirmationToken(String confirmationToken);
	Confirmation findConfirmationByUser(User user);
}