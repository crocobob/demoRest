package com.example.demo.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Products;
import com.example.demo.exception.ProductDoesNotExistException;
import com.example.demo.repositories.ProductsRepositories;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductsRepositories productRepository;
	
	@Override
	public List<Products> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public Products addProduct(Products product) {
		productRepository.insertProduct(product.getProductname(), product.getPrice());
		return productRepository.getLast();
	}

	@Override
	public Products updateProduct(Products product,Integer id) throws ProductDoesNotExistException {
		product.setId(id);
		productRepository.save(product);
		return getProductById(id);
	}
	@Override
	public Products getProductById(Integer id) throws ProductDoesNotExistException{
		return productRepository.findById(id).orElseThrow(() -> new ProductDoesNotExistException("Product not found: "+id));

	}

	@Override
	public Products updateProductActive(Integer id) throws ProductDoesNotExistException {
		productRepository.updateIsActive(id);
		return getProductById(id);
	}

	@Override
	public List<Products> getActiveProducts() {
		return productRepository.getAllActive();
	}
}
