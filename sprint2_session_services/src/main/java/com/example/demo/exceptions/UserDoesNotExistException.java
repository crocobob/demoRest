package com.example.demo.exceptions;

public class UserDoesNotExistException extends Exception{
	public UserDoesNotExistException(String message){
		super(message);
	}
}
