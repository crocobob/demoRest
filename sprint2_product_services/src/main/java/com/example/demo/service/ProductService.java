package com.example.demo.service;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.entities.Products;
import com.example.demo.exception.ProductDoesNotExistException;

public interface ProductService {

	List<Products> getProducts();

	Products addProduct(Products product);

	Products updateProduct(Products product, Integer id) throws ProductDoesNotExistException;

	Products getProductById(Integer id) throws ProductDoesNotExistException;

	Products updateProductActive(Integer id) throws ProductDoesNotExistException;

	List<Products> getActiveProducts();
	
}
