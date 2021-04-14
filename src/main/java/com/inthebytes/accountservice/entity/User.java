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
import java.util.Collection;

@Entity
public class User {
	private Integer userId;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private Boolean active;
	private Collection<Confirmation> confirmations;
	private Collection<Driver> drivers;
	private Collection<Manager> managers;
	private Collection<Order> orders;
	private Collection<Payment> payments;
	private Role role;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "username", nullable = false, length = 45)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "password", nullable = false, length = 81)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "email", nullable = false, length = 45)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "phone", nullable = false, length = 45)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Basic
	@Column(name = "first_name", nullable = false, length = 45)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Basic
	@Column(name = "last_name", nullable = false, length = 45)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Basic
	@Column(name = "active", nullable = false)
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
		if (username != null ? !username.equals(user.username) : user.username != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
		if (email != null ? !email.equals(user.email) : user.email != null) return false;
		if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
		if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
		if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
		if (active != null ? !active.equals(user.active) : user.active != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (active != null ? active.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "user")
	public Collection<Confirmation> getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(Collection<Confirmation> confirmations) {
		this.confirmations = confirmations;
	}

	@OneToMany(mappedBy = "user")
	public Collection<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(Collection<Driver> drivers) {
		this.drivers = drivers;
	}

	@OneToMany(mappedBy = "user")
	public Collection<Manager> getManagers() {
		return managers;
	}

	public void setManagers(Collection<Manager> managers) {
		this.managers = managers;
	}

	@OneToMany(mappedBy = "user")
	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(mappedBy = "user")
	public Collection<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Collection<Payment> payments) {
		this.payments = payments;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_role", referencedColumnName = "role_id", nullable = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
