package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entities.Orders;
import com.example.demo.entities.OrdersResponseEntity;
import com.example.demo.entities.Products;
import com.example.demo.exceptions.OrderDoesNotExistException;
import com.example.demo.repositories.OrdersRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrdersRepository ordersRepository;
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public OrdersResponseEntity updateToPaid(Integer orderid) throws OrderDoesNotExistException {
		getOrderById(orderid);
		ordersRepository.updateToPaid(orderid);
		Orders order = getOrderById(orderid);
		return new OrdersResponseEntity(order,getProductname(order.getProductid()));
	}

	private Orders getOrderById(Integer orderid) throws OrderDoesNotExistException {
		return ordersRepository.findById(orderid).orElseThrow(() -> new OrderDoesNotExistException("Order not found: "+orderid));
	}

	@Override
	public OrdersResponseEntity updateToDelivered(Integer orderid) throws OrderDoesNotExistException {
		getOrderById(orderid);
		ordersRepository.updateToDelivered(orderid);
		Orders order = getOrderById(orderid);
		return new OrdersResponseEntity(order,getProductname(order.getProductid()));
	}
	@Override
	public List<OrdersResponseEntity> getOrders(Integer id) {
		List<OrdersResponseEntity> list = new ArrayList();

		for(Orders order: ordersRepository.getOrdersUser(id)) {
			list.add(new OrdersResponseEntity(order, getProductname(order.getProductid())));
		}
		return list;
	}
	
	public Double getPrice(Integer id){
	ResponseEntity<Products> product= restTemplate.getForEntity("http://product-service/products/byId/"+id, Products.class); 
	return product.getBody().getPrice();
	}
	
	public String getProductname(Integer id){
	ResponseEntity<Products> product= restTemplate.getForEntity("http://product-service/products/byId/"+id, Products.class); 
	return product.getBody().getProductname();
	}
	@Override
	public OrdersResponseEntity addOrder(Orders order) throws OrderDoesNotExistException {
		Double price = getPrice(order.getProductid());
		ordersRepository.addOrder(order.getUserid(),order.getProductid(), order.getQuantity(),price, price*order.getQuantity());
		order = ordersRepository.getLast();
		return new OrdersResponseEntity(order,getProductname(order.getProductid()));
	}
	

}
