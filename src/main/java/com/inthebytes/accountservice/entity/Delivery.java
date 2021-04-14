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
import java.sql.Time;
import java.util.Collection;

@Entity
public class Delivery {
	private Integer deliveryId;
	private Integer status;
	private Time startTime;
	private Time pickupTime;
	private Time deliverTime;
	private Driver driver;
	private Collection<Order> orders;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "delivery_id", nullable = false)
	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	@Basic
	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Basic
	@Column(name = "start_time", nullable = false)
	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	@Basic
	@Column(name = "pickup_time", nullable = false)
	public Time getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Time pickupTime) {
		this.pickupTime = pickupTime;
	}

	@Basic
	@Column(name = "deliver_time", nullable = false)
	public Time getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Time deliverTime) {
		this.deliverTime = deliverTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Delivery delivery = (Delivery) o;

		if (deliveryId != null ? !deliveryId.equals(delivery.deliveryId) : delivery.deliveryId != null) return false;
		if (status != null ? !status.equals(delivery.status) : delivery.status != null) return false;
		if (startTime != null ? !startTime.equals(delivery.startTime) : delivery.startTime != null) return false;
		if (pickupTime != null ? !pickupTime.equals(delivery.pickupTime) : delivery.pickupTime != null) return false;
		if (deliverTime != null ? !deliverTime.equals(delivery.deliverTime) : delivery.deliverTime != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = deliveryId != null ? deliveryId.hashCode() : 0;
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
		result = 31 * result + (pickupTime != null ? pickupTime.hashCode() : 0);
		result = 31 * result + (deliverTime != null ? deliverTime.hashCode() : 0);
		return result;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id", referencedColumnName = "driver_id", nullable = false)
	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	@OneToMany(mappedBy = "delivery")
	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
}
