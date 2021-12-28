package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Sessions;
import com.example.demo.entities.UserLoginEntity;
import com.example.demo.exceptions.InvalidCredentialException;
import com.example.demo.services.SessionService;

@RestController
@EnableEurekaClient
@RequestMapping("/session")
public class SessionController{
	@Autowired
	SessionService sessionService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginEntity ent, BindingResult bindingResults) {
		try {
			Sessions session =sessionService.login(ent.getUsername(), ent.getPassword());
			return new ResponseEntity<Sessions>(session, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/checker/{username}")
	public ResponseEntity<?> checker(@PathVariable String username){
		try {
			Sessions session = sessionService.checker(username);
			return new ResponseEntity<Sessions>(session, HttpStatus.ACCEPTED);
		} catch (Exception e) {
;			return new ResponseEntity<Sessions>(new Sessions(null,null,null), HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping("/logout/{username}")
	public ResponseEntity<?> logout(@PathVariable String username){
		try {
			sessionService.logout(username);
			return new ResponseEntity<String>("Logout Success please comeback again", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
	
	

