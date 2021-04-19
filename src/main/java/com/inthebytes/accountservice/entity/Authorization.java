package com.inthebytes.accountservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Authorization {

	@Id
	@Column(name = "token", nullable = false, length = 256)
	private String token;

	@Basic
	@Column(name = "expiration_date", nullable = false)
	private Timestamp expirationDate;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Authorization that = (Authorization) o;

		if (token != null ? !token.equals(that.token) : that.token != null) return false;
		if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = token != null ? token.hashCode() : 0;
		result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
		return result;
	}
}
