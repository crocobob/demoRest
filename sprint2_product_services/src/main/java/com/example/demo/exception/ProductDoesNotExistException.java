package com.example.demo.exception;

public class ProductDoesNotExistException extends Exception{
	public ProductDoesNotExistException(String message){
		super(message);
	}
}
