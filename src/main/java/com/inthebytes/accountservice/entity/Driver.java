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
public class Driver {
	private Integer driverId;
	private Integer vehicleId;
	private Integer financialId;
	private Collection<Delivery> deliveries;
	private User user;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "driver_id", nullable = false)
	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	@Basic
	@Column(name = "vehicle_id", nullable = false)
	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	@Basic
	@Column(name = "financial_id", nullable = false)
	public Integer getFinancialId() {
		return financialId;
	}

	public void setFinancialId(Integer financialId) {
		this.financialId = financialId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Driver driver = (Driver) o;

		if (driverId != null ? !driverId.equals(driver.driverId) : driver.driverId != null) return false;
		if (vehicleId != null ? !vehicleId.equals(driver.vehicleId) : driver.vehicleId != null) return false;
		if (financialId != null ? !financialId.equals(driver.financialId) : driver.financialId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = driverId != null ? driverId.hashCode() : 0;
		result = 31 * result + (vehicleId != null ? vehicleId.hashCode() : 0);
		result = 31 * result + (financialId != null ? financialId.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "driver")
	public Collection<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(Collection<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
