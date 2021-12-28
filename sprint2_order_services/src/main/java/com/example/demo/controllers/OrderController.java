package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entities.Orders;
import com.example.demo.entities.OrdersResponseEntity;
import com.example.demo.services.OrderService;

@RestController
@EnableEurekaClient
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	OrderService orderService;
	
	@PutMapping("/update/paid/{orderid}")
	public ResponseEntity<?> updatePaid(@PathVariable("orderid") Integer orderid){
		OrdersResponseEntity order;
		try {
			order = orderService.updateToPaid(orderid);
			return new ResponseEntity<OrdersResponseEntity>(order,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/update/delivered/{orderid}")
	public ResponseEntity<?> updateDelivered(@PathVariable("orderid") Integer orderid){
		OrdersResponseEntity order;
		try {
			order = orderService.updateToDelivered(orderid);
			return new ResponseEntity<OrdersResponseEntity>(order,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/get/byUser/{userid}")
	public ResponseEntity<?> adminUpdate(@PathVariable Integer userid){
		try {
			List<OrdersResponseEntity> list = orderService.getOrders(userid);
			return new ResponseEntity<List<OrdersResponseEntity>>(list,HttpStatus.ACCEPTED);
		} catch (Exception exc) {
			return new ResponseEntity<String>(exc.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addOrder(@RequestBody Orders order){
		try {
			return new ResponseEntity<OrdersResponseEntity>(orderService.addOrder(order), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
