package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.services.ValidationService;
import com.example.demo.entities.AdminUpdateEntity;
import com.example.demo.entities.AdminValidationEntity;
import com.example.demo.entities.Sessions;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.IdNotInt;
import com.example.demo.services.UserService;


@RestController
@EnableEurekaClient
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired 
	private ValidationService validationService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/all")
	public List<Users> viewAll() {
		return userService.getUsers();
	}
	@PostMapping("/add/customer")
	public ResponseEntity<?> addCustomer(@Valid @RequestBody Users user, BindingResult bindingResults) {
		Map<String , String> errors = validationService.validate(bindingResults);
		
		if(errors.isEmpty()) {
			try {
				return new ResponseEntity(userService.addCustomer(user), HttpStatus.ACCEPTED);
			} catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		else
			return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/add/admin/{username}")
	public ResponseEntity<?> addAdmon(@PathVariable("username") String username,@Valid @RequestBody Users user, BindingResult bindingResults) {
		Map<String , String> errors = validationService.validate(bindingResults);
		
		if(errors.isEmpty()) {
			try {
				restTemplate.getForObject("http://session-service/session/checker/"+username, Sessions.class);
				return new ResponseEntity(userService.addAdmin(user,username), HttpStatus.ACCEPTED);
			} catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		else
			return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
	}
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		try {
			Users u = userService.getUserById(id);
			return new ResponseEntity(u,HttpStatus.ACCEPTED);
		} catch (Exception exc) {
			return new ResponseEntity(exc.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}
	@GetMapping("/allpending")
	public ResponseEntity<?> viewAllPending() {
		List<Users> users = userService.getPendingUsers();
		return new ResponseEntity(users, HttpStatus.ACCEPTED);
	}
	@PostMapping("/validate")
	public ResponseEntity<?> adminValidate(@RequestBody AdminValidationEntity ent, BindingResult bindingResults) {
		try {
			restTemplate.getForObject("http://session-service/session/checker/"+ent.getUsername(), Sessions.class);
			userService.validateUsers(ent);
		} 
		catch(HttpClientErrorException e) {
			return new ResponseEntity("Session Timeout: "+ent.getUsername()+" isn't login ",HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Update Success", HttpStatus.ACCEPTED);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> adminUpdate(@PathVariable Integer id, @Valid @RequestBody AdminUpdateEntity ent, BindingResult bindingResults) {
		Map<String , String> errors = validationService.validate(bindingResults);
		if(errors.isEmpty()) {
			try {
				restTemplate.getForObject("http://session-service/session/checker/"+ent.getAdminusername(), Sessions.class);

				Users user = userService.updateUser(id, ent);
				return new ResponseEntity<Users>(user, HttpStatus.ACCEPTED);
			}
			catch(HttpClientErrorException e) {
				return new ResponseEntity("Session Timeout: "+ent.getAdminusername()+" isn't login ",HttpStatus.BAD_REQUEST);
			}
			catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		}
		else
			return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("/delete/{username}/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("username") String username, @PathVariable("id") Integer id){
		try {
			restTemplate.getForObject("http://session-service/session/checker/"+username, Sessions.class);

			Users user = userService.deleteEmployee(id, username);
			return new ResponseEntity(user,HttpStatus.ACCEPTED);
		}	
		catch(HttpClientErrorException e) {
			return new ResponseEntity("Session Timeout: "+username+" isn't login ",HttpStatus.BAD_REQUEST);
		}
		catch(Exception  exc) {
			return new ResponseEntity(exc.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}
