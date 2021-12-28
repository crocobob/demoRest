package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entities.Products;
import com.example.demo.entities.Sessions;
import com.example.demo.service.ProductService;

@RestController
@EnableEurekaClient
@RequestMapping("/products")
public class ProductsController {
	@Autowired
	ProductService productService; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/all")
	public List<Products> viewAll() {
		return productService.getProducts();
	}
	@GetMapping("/byId/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		try {
			return new ResponseEntity<Products>(productService.getProductById(id),HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	@GetMapping("/all/active")
	public List<Products> viewAllActive() {
		return productService.getActiveProducts();
	}
	
	@PostMapping("/add/{username}")
	public ResponseEntity<?> addProduct(@PathVariable String username,@Valid @RequestBody Products product, BindingResult bindingResults) {
		try {
			restTemplate.getForObject("http://session-service/session/checker/"+username, Sessions.class);
			return new ResponseEntity<Products>(productService.addProduct(product), HttpStatus.ACCEPTED);
		} 
		catch(HttpClientErrorException e) {
			return new ResponseEntity<String>("Session Timeout: "+username+" isn't login ",HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	@PutMapping("/update/{username}/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable String username,@PathVariable Integer id,@Valid @RequestBody Products product, BindingResult bindingResults){
		try {
			restTemplate.getForObject("http://session-service/session/checker/"+username, Sessions.class);
			return new ResponseEntity<Products>(productService.updateProduct(product,id), HttpStatus.ACCEPTED);
		} 
		catch(HttpClientErrorException e) {
			return new ResponseEntity<String>("Session Timeout: "+username+" isn't login ",HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@PutMapping("/update/active/{username}/{id}")
	public ResponseEntity<?> updateProductActive(@PathVariable String username,@PathVariable Integer id){
		try {
			restTemplate.getForObject("http://session-service/session/checker/"+username, Sessions.class);
			return new ResponseEntity<Products>(productService.updateProductActive(id), HttpStatus.ACCEPTED);
		} 
		catch(HttpClientErrorException e) {
			return new ResponseEntity<String>("Session Timeout: "+username+" isn't login ",HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
}
