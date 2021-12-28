package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.UpdateEntity;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.repositories.UsersRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	UsersRepository usersRepository;
	
	@Override
	public Users getUserById(Integer id) throws UserDoesNotExistException {
		return usersRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("User not found: "+id));
	}

	@Override
	public Users updateUser(Integer id, UpdateEntity ent) throws UserDoesNotExistException {
		usersRepository.updateUser(id, ent.getUsername(),ent.getFirstname(),ent.getLastname());
		return getUserById(id);
	}
}
