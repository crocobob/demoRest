package com.example.demo.exceptions;

public class OrderDoesNotExistException extends Exception{
	public OrderDoesNotExistException(String message){
		super(message);
	}
}
