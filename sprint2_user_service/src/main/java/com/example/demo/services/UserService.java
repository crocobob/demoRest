package com.example.demo.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.example.demo.entities.AdminUpdateEntity;
import com.example.demo.entities.AdminValidationEntity;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.IdNotInt;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.exceptions.UserNotAdminException;
import com.example.demo.exceptions.UsernameAlreadyExistException;

public interface UserService {
	public List<Users>getUsers();

	public Users addCustomer(Users user) throws UsernameAlreadyExistException;

	public Users getUserById(Integer id) throws UserDoesNotExistException;
	
	public List<Users> getPendingUsers();

	public void validateUsers(AdminValidationEntity ent) throws IdNotInt, UserNotAdminException;

	public Users updateUser(Integer id,AdminUpdateEntity ent) throws UserNotAdminException, UserDoesNotExistException;

	public Users deleteEmployee(Integer id, String username) throws UserDoesNotExistException, UserNotAdminException;

	public Users addAdmin(@Valid Users user, String username)throws UsernameAlreadyExistException, UserNotAdminException;
}
