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
public class Transaction {
	private Integer transactionId;
	private Float subtotal;
	private Float tax;
	private Float fee;
	private Float discount;
	private Float tip;
	private Float total;
	private Timestamp paymentTime;
	private Collection<Order> orders;
	private Payment payment;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "transaction_id", nullable = false)
	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	@Basic
	@Column(name = "subtotal", nullable = false, precision = 0)
	public Float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}

	@Basic
	@Column(name = "tax", nullable = false, precision = 0)
	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	@Basic
	@Column(name = "fee", nullable = false, precision = 0)
	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	@Basic
	@Column(name = "discount", nullable = false, precision = 0)
	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	@Basic
	@Column(name = "tip", nullable = false, precision = 0)
	public Float getTip() {
		return tip;
	}

	public void setTip(Float tip) {
		this.tip = tip;
	}

	@Basic
	@Column(name = "total", nullable = false, precision = 0)
	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	@Basic
	@Column(name = "payment_time", nullable = false)
	public Timestamp getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Transaction that = (Transaction) o;

		if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
			return false;
		if (subtotal != null ? !subtotal.equals(that.subtotal) : that.subtotal != null) return false;
		if (tax != null ? !tax.equals(that.tax) : that.tax != null) return false;
		if (fee != null ? !fee.equals(that.fee) : that.fee != null) return false;
		if (discount != null ? !discount.equals(that.discount) : that.discount != null) return false;
		if (tip != null ? !tip.equals(that.tip) : that.tip != null) return false;
		if (total != null ? !total.equals(that.total) : that.total != null) return false;
		if (paymentTime != null ? !paymentTime.equals(that.paymentTime) : that.paymentTime != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = transactionId != null ? transactionId.hashCode() : 0;
		result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
		result = 31 * result + (tax != null ? tax.hashCode() : 0);
		result = 31 * result + (fee != null ? fee.hashCode() : 0);
		result = 31 * result + (discount != null ? discount.hashCode() : 0);
		result = 31 * result + (tip != null ? tip.hashCode() : 0);
		result = 31 * result + (total != null ? total.hashCode() : 0);
		result = 31 * result + (paymentTime != null ? paymentTime.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "transaction")
	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id", referencedColumnName = "payment_id", nullable = false)
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
