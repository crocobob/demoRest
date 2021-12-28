package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ValidationService {
	public Map<String, String> validate(BindingResult bindingresult){
		Map<String, String> map  = new HashMap<>();
		
		for(FieldError fieldError: bindingresult.getFieldErrors()) {
			map.put(fieldError.getField(), fieldError.getDefaultMessage());
			System.out.println(fieldError.getDefaultMessage());
		}
		return map;
	}
}
