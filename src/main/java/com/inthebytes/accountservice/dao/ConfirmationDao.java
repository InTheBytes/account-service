package com.inthebytes.accountservice.dao;

import com.inthebytes.accountservice.entity.Confirmation;
import com.inthebytes.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationDao extends JpaRepository<Confirmation, String> {
	Confirmation findByConfirmationToken(String confirmationToken);
	Confirmation findConfirmationByUser(User user);
}