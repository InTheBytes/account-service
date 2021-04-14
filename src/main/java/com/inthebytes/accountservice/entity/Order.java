package com.inthebytes.accountservice.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Order {
	private Integer orderId;
	private Timestamp windowStart;
	private Timestamp windowEnd;
	private String specialInstructions;
	private User user;
	private Restaurant restaurant;
	private Transaction transaction;
	private Location destination;
	private Delivery delivery;
	private Collection<Food> foods;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "order_id", nullable = false)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Basic
	@Column(name = "window_start", nullable = false)
	public Timestamp getWindowStart() {
		return windowStart;
	}

	public void setWindowStart(Timestamp windowStart) {
		this.windowStart = windowStart;
	}

	@Basic
	@Column(name = "window_end", nullable = false)
	public Timestamp getWindowEnd() {
		return windowEnd;
	}

	public void setWindowEnd(Timestamp windowEnd) {
		this.windowEnd = windowEnd;
	}

	@Basic
	@Column(name = "special_instructions", nullable = false, length = 200)
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Order order = (Order) o;

		if (orderId != null ? !orderId.equals(order.orderId) : order.orderId != null) return false;
		if (windowStart != null ? !windowStart.equals(order.windowStart) : order.windowStart != null) return false;
		if (windowEnd != null ? !windowEnd.equals(order.windowEnd) : order.windowEnd != null) return false;
		if (specialInstructions != null ? !specialInstructions.equals(order.specialInstructions) : order.specialInstructions != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = orderId != null ? orderId.hashCode() : 0;
		result = 31 * result + (windowStart != null ? windowStart.hashCode() : 0);
		result = 31 * result + (windowEnd != null ? windowEnd.hashCode() : 0);
		result = 31 * result + (specialInstructions != null ? specialInstructions.hashCode() : 0);
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id", nullable = false)
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id", nullable = false)
	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destination_id", referencedColumnName = "location_id", nullable = false)
	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id", nullable = false)
	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(name = "order_food",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "food_id")
	)
	public Collection<Food> getFoods() {
		return foods;
	}

	public void setFoods(Collection<Food> foods) {
		this.foods = foods;
	}
}
