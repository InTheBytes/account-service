package com.inthebytes.accountservice.dao;

import com.inthebytes.accountservice.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationDao extends JpaRepository<Authorization, String> {
}
