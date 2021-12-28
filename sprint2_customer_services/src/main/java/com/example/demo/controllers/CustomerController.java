package com.example.demo.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.UpdateEntity;
import com.example.demo.entities.Users;
import com.example.demo.services.CustomerService;
import com.example.demo.services.ValidationService;

@RestController
@EnableEurekaClient
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ValidationService validationService;
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		try {
			Users u = customerService.getUserById(id);
			return new ResponseEntity<Users>(u,HttpStatus.ACCEPTED);
		} catch (Exception exc) {
			return new ResponseEntity<String>(exc.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> adminUpdate(@PathVariable Integer id, @Valid @RequestBody UpdateEntity ent, BindingResult bindingResults) {
		Map<String , String> errors = validationService.validate(bindingResults);
		if(errors.isEmpty()) {
			try {
				Users user = customerService.updateUser(id, ent);
				return new ResponseEntity<Users>(user, HttpStatus.ACCEPTED);
			}
			catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		else
			return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
	}

}
