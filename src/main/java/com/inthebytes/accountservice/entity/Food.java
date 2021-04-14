package com.inthebytes.accountservice.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Collection;

@Entity
public class Food {
	private Integer foodId;
	private String name;
	private Float price;
	private String description;
	private Restaurant restaurant;
	private Collection<Order> orders;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "food_id", nullable = false)
	public Integer getFoodId() {
		return foodId;
	}

	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
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
	@Column(name = "price", nullable = false, precision = 0)
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Basic
	@Column(name = "description", nullable = false, length = 100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Food food = (Food) o;

		if (foodId != null ? !foodId.equals(food.foodId) : food.foodId != null) return false;
		if (name != null ? !name.equals(food.name) : food.name != null) return false;
		if (price != null ? !price.equals(food.price) : food.price != null) return false;
		if (description != null ? !description.equals(food.description) : food.description != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = foodId != null ? foodId.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id", nullable = false)
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@ManyToMany(mappedBy = "foods")
	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
}
