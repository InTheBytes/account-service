package com.inthebytes.accountservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
public class Confirmation {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	    name = "UUID",
	    strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "token_id", nullable = false)
	private String tokenId;

	@Basic
	@Column(name = "confirmation_token", nullable = false, length = 256)
	private String confirmationToken;

	@Basic
	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Basic
	@Column(name = "is_confirmed", nullable = false)
	private Boolean isConfirmed;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	private User user;

	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getConfirmed() {
		return isConfirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		isConfirmed = confirmed;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Confirmation that = (Confirmation) o;

		if (tokenId != null ? !tokenId.equals(that.tokenId) : that.tokenId != null) return false;
		if (confirmationToken != null ? !confirmationToken.equals(that.confirmationToken) : that.confirmationToken != null)
			return false;
		if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
		if (isConfirmed != null ? !isConfirmed.equals(that.isConfirmed) : that.isConfirmed != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = tokenId != null ? tokenId.hashCode() : 0;
		result = 31 * result + (confirmationToken != null ? confirmationToken.hashCode() : 0);
		result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
		result = 31 * result + (isConfirmed != null ? isConfirmed.hashCode() : 0);
		return result;
	}
}
