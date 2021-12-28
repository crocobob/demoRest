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
@Entity
public class Orders {
	@Id
	private Integer id;
	private Integer userid;
	private Integer productid;
	private Integer quantity;
	private boolean ispaid;
	private boolean isdelivered;
	private Timestamp dateordered;
	private Timestamp datepaid;
	private Timestamp datedelivered;
	private Double priceeach;
	private Double pricetotal;
	boolean getIsPaid() {
		return ispaid;
	}

	public boolean getIsDelivered() {
		return isdelivered;
	}
}
