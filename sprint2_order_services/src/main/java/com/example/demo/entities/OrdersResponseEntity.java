package com.example.demo.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseEntity {
	private Integer userid;
	private String productname;
	private Integer quantity;
	private boolean ispaid;
	private boolean isdelivered;
	private Timestamp dateordered;
	private Timestamp datepaid;
	private Timestamp datedelivered;
	
	public OrdersResponseEntity(Orders order, String productname){
		this.userid = order.getUserid();
		this.quantity =order.getQuantity();
		this.ispaid = order.getIsPaid();
		this.isdelivered = order.getIsDelivered();
		this.dateordered = order.getDateordered();
		this.datedelivered = order.getDatedelivered();
		this.datepaid = order.getDatepaid();
		this.productname = productname;
	}
	
	boolean getIsPaid() {
		return ispaid;
	}

	public boolean getIsDelivered() {
		return isdelivered;
	}
}
