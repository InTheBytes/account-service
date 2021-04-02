package com.inthebytes.accountservice.dao;

import com.inthebytes.accountservice.entity.UserConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfirmationDao extends JpaRepository<UserConfirmation, Long> {
	UserConfirmation findByConfirmationToken(String confirmationToken);
}