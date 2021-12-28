package com.example.demo.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Orders;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
	@Transactional
	@Modifying
	@Query(value = "UPDATE orders SET ispaid= 1, datepaid = NOW() WHERE id = :id",
			nativeQuery = true)
	void updateToPaid(@Param("id")Integer orderid);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE orders SET isdelivered= 1, datedelivered = NOW() WHERE id = :id",
			nativeQuery = true)
	void updateToDelivered(@Param("id")Integer orderid);
	
	@Query(value = "SELECT * FROM Orders WHERE userid = :id",
			nativeQuery = true)
	List<Orders> getOrdersUser(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query(	value = "INSERT INTO Orders (userid, productid,quantity,priceeach,pricetotal)"
			+ "VALUES (:userid,:productid,:quantity, :priceeach, :pricetotal)",
			nativeQuery = true)
	void addOrder(@Param("userid") Integer userid,@Param("productid") Integer productid,@Param("quantity") Integer quantity,@Param("priceeach") Double price,@Param("pricetotal") Double d);
	
	@Query(	value = "SELECT * FROM Orders "
			+ 		"ORDER BY id DESC "
			+		 "LIMIT 1;  ",
	nativeQuery = true)
	Orders getLast();
	
}
