package com.example.demo.services;

import java.util.List;

import org.springframework.util.MultiValueMap;

import com.example.demo.entities.Orders;
import com.example.demo.entities.OrdersResponseEntity;
import com.example.demo.exceptions.OrderDoesNotExistException;

public interface OrderService {

	OrdersResponseEntity updateToPaid(Integer orderid) throws OrderDoesNotExistException;

	OrdersResponseEntity updateToDelivered(Integer orderid) throws OrderDoesNotExistException;

	List<OrdersResponseEntity> getOrders(Integer userid);

	OrdersResponseEntity addOrder(Orders order) throws OrderDoesNotExistException;

}
