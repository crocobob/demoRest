package com.example.demo.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>  {
	
	@Query(value = "SELECT * FROM users WHERE username = :username AND password = :password",
			nativeQuery = true)
	public Users getLogin(@Param("username") String username, @Param("password") String password);
	
}
