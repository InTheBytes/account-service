package com.inthebytes.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="user_confirmation")
public class UserConfirmation {
	private Long tokenId;
	private String confirmationToken;
	private Long userId;
	private Timestamp createdDate;
	private Boolean isConfirmed;
	private User userByUserId;

	@Id
	@Column(name = "token_id", nullable = false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	@Basic
	@Column(name = "confirmation_token", nullable = false, length = 255)
	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	@Basic
	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "created_date", nullable = false)
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Basic
	@Column(name = "is_confirmed", nullable = false)
	public Boolean getConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		isConfirmed = confirmed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserConfirmation that = (UserConfirmation) o;

		if (tokenId != null ? !tokenId.equals(that.tokenId) : that.tokenId != null) return false;
		if (confirmationToken != null ? !confirmationToken.equals(that.confirmationToken) : that.confirmationToken != null)
			return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
		if (isConfirmed != null ? !isConfirmed.equals(that.isConfirmed) : that.isConfirmed != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = tokenId != null ? tokenId.hashCode() : 0;
		result = 31 * result + (confirmationToken != null ? confirmationToken.hashCode() : 0);
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
		result = 31 * result + (isConfirmed != null ? isConfirmed.hashCode() : 0);
		return result;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
	@JsonBackReference
	public User getUserByUserId() {
		return userByUserId;
	}

	public void setUserByUserId(User userByUserId) {
		this.userByUserId = userByUserId;
	}
}
