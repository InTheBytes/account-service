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
public class Restaurant {
	private Integer restaurantId;
	private String name;
	private String cuisine;
	private Collection<Food> foods;
	private Collection<Manager> managers;
	private Collection<Order> orders;
	private Location location;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "restaurant_id", nullable = false)
	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Basic
	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "cuisine", nullable = false, length = 45)
	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Restaurant that = (Restaurant) o;

		if (restaurantId != null ? !restaurantId.equals(that.restaurantId) : that.restaurantId != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (cuisine != null ? !cuisine.equals(that.cuisine) : that.cuisine != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = restaurantId != null ? restaurantId.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (cuisine != null ? cuisine.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "restaurant")
	public Collection<Food> getFoods() {
		return foods;
	}

	public void setFoods(Collection<Food> foods) {
		this.foods = foods;
	}

	@OneToMany(mappedBy = "restaurant")
	public Collection<Manager> getManagers() {
		return managers;
	}

	public void setManagers(Collection<Manager> managers) {
		this.managers = managers;
	}

	@OneToMany(mappedBy = "restaurant")
	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false)
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
