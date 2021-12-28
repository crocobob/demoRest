package com.example.demo.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Products;

public interface ProductsRepositories extends JpaRepository<Products,Integer> {

	@Transactional
	@Modifying
	@Query(	value = "INSERT INTO Products (productname, price) "
			+ "VALUES (:productname, :price)",
			nativeQuery = true)
	void insertProduct(@Param("productname") String productname, @Param("price") Double price);
	
	@Query(	value = "SELECT * FROM products "
			+ 		"ORDER BY id DESC "
			+		 "LIMIT 1;  ",
	nativeQuery = true)
	Products getLast();
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Products SET isactive = 0 WHERE id = :id",
			nativeQuery = true)
	public void updateIsActive(@Param("id")int id);
	
	@Query(value = "SELECT * FROM Products WHERE isactive = 1",
			nativeQuery = true)
	List<Products> getAllActive();
	
}
