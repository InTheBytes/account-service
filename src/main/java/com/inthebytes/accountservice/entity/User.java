package com.inthebytes.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name="user")
public class User {
	private Long userId;
	private Long userRole;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private Role roleByUserRole;
	private Collection<UserConfirmation> userConfirmationsByUserId;
	private Boolean active;
//	private Collection<Driver> driversByUserId;
//	private Collection<Payment> paymentsByUserId;
//	private Collection<Restaurant> restaurantsByUserId;
//	private Collection<UserLocation> userLocationsByUserId;
//	private Collection<UserOrder> userOrdersByUserId;

	@Id
	@Column(name = "user_id", nullable = false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "user_role", nullable = false)
	public Long getUserRole() {
		return userRole;
	}

	public void setUserRole(Long userRole) {
		this.userRole = userRole;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
		if (userRole != null ? !userRole.equals(user.userRole) : user.userRole != null) return false;
		if (username != null ? !username.equals(user.username) : user.username != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
		if (email != null ? !email.equals(user.email) : user.email != null) return false;
		if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
		if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
		if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		return result;
	}

	@ManyToOne
	@JsonBackReference
	@JoinColumns(@JoinColumn(name = "user_role", referencedColumnName = "role_id", nullable = false, insertable = false, updatable = false))
	public Role getRoleByUserRole() {
		return roleByUserRole;
	}

	public void setRoleByUserRole(Role roleByUserRole) {
		this.roleByUserRole = roleByUserRole;
	}

	@OneToMany(mappedBy = "userByUserId")
	@JsonManagedReference
	public Collection<UserConfirmation> getUserConfirmationsByUserId() {
		return userConfirmationsByUserId;
	}

	public void setUserConfirmationsByUserId(Collection<UserConfirmation> userConfirmationsByUserId) {
		this.userConfirmationsByUserId = userConfirmationsByUserId;
	}

	@Basic
	@Column(name = "active", nullable = false)
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

//	@OneToMany(mappedBy = "userByUserId")
//	public Collection<Driver> getDriversByUserId() {
//		return driversByUserId;
//	}
//
//	public void setDriversByUserId(Collection<Driver> driversByUserId) {
//		this.driversByUserId = driversByUserId;
//	}
//
//	@OneToMany(mappedBy = "userByUserId")
//	public Collection<Payment> getPaymentsByUserId() {
//		return paymentsByUserId;
//	}
//
//	public void setPaymentsByUserId(Collection<Payment> paymentsByUserId) {
//		this.paymentsByUserId = paymentsByUserId;
//	}
//
//	@OneToMany(mappedBy = "userByManagerId")
//	public Collection<Restaurant> getRestaurantsByUserId() {
//		return restaurantsByUserId;
//	}
//
//	public void setRestaurantsByUserId(Collection<Restaurant> restaurantsByUserId) {
//		this.restaurantsByUserId = restaurantsByUserId;
//	}
//
//	@OneToMany(mappedBy = "userByUserId")
//	public Collection<UserLocation> getUserLocationsByUserId() {
//		return userLocationsByUserId;
//	}
//
//	public void setUserLocationsByUserId(Collection<UserLocation> userLocationsByUserId) {
//		this.userLocationsByUserId = userLocationsByUserId;
//	}
//
//	@OneToMany(mappedBy = "userByUserId")
//	public Collection<UserOrder> getUserOrdersByUserId() {
//		return userOrdersByUserId;
//	}
//
//	public void setUserOrdersByUserId(Collection<UserOrder> userOrdersByUserId) {
//		this.userOrdersByUserId = userOrdersByUserId;
//	}
}