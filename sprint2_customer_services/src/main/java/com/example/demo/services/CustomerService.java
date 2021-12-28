package com.example.demo.services;

import com.example.demo.entities.UpdateEntity;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.UserDoesNotExistException;

public interface CustomerService {

	Users getUserById(Integer id) throws UserDoesNotExistException;

	Users updateUser(Integer id, UpdateEntity ent) throws UserDoesNotExistException;

}
