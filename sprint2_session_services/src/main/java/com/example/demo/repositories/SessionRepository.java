package com.example.demo.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Sessions;
import com.example.demo.entities.Users;

public interface SessionRepository extends JpaRepository<Sessions, String>{
	@Transactional
	@Modifying
	@Query(	value = "INSERT INTO sessions (username, expiredtimestamp) "
			+ "VALUES (:username , NOW() + INTERVAL 5 HOUR)",
			nativeQuery = true)
    public void addSession(@Param("username") String username);
	
	@Transactional
	@Modifying
	@Query(	value = "UPDATE sessions SET username = :username, loginTimestamp = NOW(), expiredtimestamp = NOW() + INTERVAL 5 HOUR",
			nativeQuery = true)
	public void updateSession(@Param("username") String username);
	
	@Query(value = "SELECT * FROM sessions WHERE username = :username AND expiredtimestamp > Now()",
			nativeQuery = true)
	public Sessions checker(@Param("username") String username);
	
}
