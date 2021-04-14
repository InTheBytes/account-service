package com.inthebytes.accountservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Payment {
	private Integer paymentId;
	private String method;
	private String nickname;
	private Timestamp expiration;
	private String accountNum;
	private String code;
	private String holderName;
	private User user;
	private Collection<Transaction> transactions;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "payment_id", nullable = false)
	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	@Basic
	@Column(name = "method", nullable = false, length = 45)
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Basic
	@Column(name = "nickname", nullable = false, length = 45)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Basic
	@Column(name = "expiration", nullable = false)
	public Timestamp getExpiration() {
		return expiration;
	}

	public void setExpiration(Timestamp expiration) {
		this.expiration = expiration;
	}

	@Basic
	@Column(name = "account_num", nullable = false, length = 45)
	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	@Basic
	@Column(name = "code", nullable = false, length = 45)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Basic
	@Column(name = "holder_name", nullable = false, length = 45)
	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Payment payment = (Payment) o;

		if (paymentId != null ? !paymentId.equals(payment.paymentId) : payment.paymentId != null) return false;
		if (method != null ? !method.equals(payment.method) : payment.method != null) return false;
		if (nickname != null ? !nickname.equals(payment.nickname) : payment.nickname != null) return false;
		if (expiration != null ? !expiration.equals(payment.expiration) : payment.expiration != null) return false;
		if (accountNum != null ? !accountNum.equals(payment.accountNum) : payment.accountNum != null) return false;
		if (code != null ? !code.equals(payment.code) : payment.code != null) return false;
		if (holderName != null ? !holderName.equals(payment.holderName) : payment.holderName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = paymentId != null ? paymentId.hashCode() : 0;
		result = 31 * result + (method != null ? method.hashCode() : 0);
		result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
		result = 31 * result + (expiration != null ? expiration.hashCode() : 0);
		result = 31 * result + (accountNum != null ? accountNum.hashCode() : 0);
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (holderName != null ? holderName.hashCode() : 0);
		return result;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "payment")
	public Collection<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Collection<Transaction> transactions) {
		this.transactions = transactions;
	}
}
