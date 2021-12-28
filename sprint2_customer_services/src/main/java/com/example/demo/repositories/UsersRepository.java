package com.example.demo.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>  {
	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET username = :username, firstname = :firstname, lastname = :lastname WHERE userid = :id",
			nativeQuery = true)
	void updateUser(@Param("id")Integer id, @Param("username")String username, @Param("firstname")String firstname, @Param("lastname")String lastname);

}
