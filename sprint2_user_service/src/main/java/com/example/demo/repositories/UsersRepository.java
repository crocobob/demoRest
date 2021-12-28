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
	
	@Transactional
	@Modifying
	@Query(	value = "INSERT INTO Users (username, password, position, firstname, lastname, ispending) "
			+ "VALUES (:username , :pass , 'customer', :firstname ,:lastname, 1)",
			nativeQuery = true)
    public void insertCustomer(@Param("username") String username,@Param("pass")String password,@Param("firstname")String firstname, @Param("lastname")String lastname);
	
	@Transactional
	@Modifying
	@Query(	value = "INSERT INTO Users (username, password, position, firstname, lastname, ispending) "
			+ "VALUES (:username , :pass , 'admin', :firstname ,:lastname, 1)",
			nativeQuery = true)
	public void insertAdmin(@Param("username") String username,@Param("pass")String password,@Param("firstname")String firstname, @Param("lastname")String lastname);
	
	@Query(value = "SELECT * FROM users WHERE username = :username",
			nativeQuery = true)
	public Users getByUsername(@Param("username") String username);
	
	@Query(value = "SELECT * FROM users WHERE ispending = 1",
			nativeQuery = true)
	public List<Users> getPendingUsers();
	
	@Query(value = "SELECT * FROM users WHERE position = 'admin' and username = :username",
			nativeQuery = true)
	public Users getAdminByUsername(@Param("username") String usrename);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET ispending = 0 WHERE userid = :id",
			nativeQuery = true)
	public void validate(@Param("id")int id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET username = :username, firstname = :firstname, lastname = :lastname WHERE userid = :id",
			nativeQuery = true)
	public void updateUser(@Param("id")Integer id, @Param("username")String username, @Param("firstname")String firstname, @Param("lastname")String lastname);

	@Query(value = "SELECT * FROM users WHERE username = :username AND password = :password",
			nativeQuery = true)
	public Users getLogin(@Param("username") String username, @Param("password") String password);

	
}
