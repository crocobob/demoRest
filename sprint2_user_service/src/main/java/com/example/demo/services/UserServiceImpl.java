package com.example.demo.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.AdminUpdateEntity;
import com.example.demo.entities.AdminValidationEntity;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.IdNotInt;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.exceptions.UserNotAdminException;
import com.example.demo.exceptions.UsernameAlreadyExistException;
import com.example.demo.helper.IdSeperator;
import com.example.demo.repositories.UsersRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public List<Users> getUsers() {
		return usersRepository.findAll();
	}

	@Override
	public Users addCustomer(Users user) throws UsernameAlreadyExistException {
		if(usersRepository.getByUsername(user.getUsername()) == null) {
			usersRepository.insertCustomer(user.getUsername(), user.getPassword(),user.getFirstname() ,user.getLastname());
			return usersRepository.getByUsername(user.getUsername());
		}
		throw new UsernameAlreadyExistException("Username already been used");
	}


	@Override
	public List<Users> getPendingUsers() {
		return usersRepository.getPendingUsers();
	}

	@Override
	public void validateUsers(AdminValidationEntity ent) throws IdNotInt, UserNotAdminException {
		if(usersRepository.getAdminByUsername(ent.getUsername()) != null) {
			int[] ids = new IdSeperator().seperator(ent.getIds());
			for( int id : ids) {
				usersRepository.validate(id);
			}
		}
		else
			throw new UserNotAdminException("Unauthorized to validate Users");
		
		
	}

	@Override
	public Users updateUser(Integer id, AdminUpdateEntity ent) throws UserNotAdminException, UserDoesNotExistException {
		if(usersRepository.getAdminByUsername(ent.getAdminusername()) != null) {
				usersRepository.updateUser(id, ent.getUsername(),ent.getFirstname(),ent.getLastname());
				return getUserById(id);
		}
		else
			throw new UserNotAdminException("Unauthorized to update Users");
	}
	
	@Override
	public Users getUserById(Integer id) throws UserDoesNotExistException {
		return usersRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException("User not found: "+id));
	}

	@Override
	public Users deleteEmployee(Integer id, String username) throws UserDoesNotExistException, UserNotAdminException {
		if(usersRepository.getAdminByUsername(username) != null) {
			Users user = getUserById(id);
			usersRepository.delete(user);
			return user;
		}
		else
			throw new UserNotAdminException("Unauthorized to delete Users");
	}

	@Override
	public Users addAdmin(@Valid Users user, String username) throws UsernameAlreadyExistException, UserNotAdminException {
		if(usersRepository.getAdminByUsername(username) != null) {
			if(usersRepository.getByUsername(user.getUsername()) == null) {
				usersRepository.insertAdmin(user.getUsername(), user.getPassword(),user.getFirstname() ,user.getLastname());
				return usersRepository.getByUsername(user.getUsername());
			}
		}
		else
			throw new UserNotAdminException("Unauthorized to Add New Admin");
		throw new UsernameAlreadyExistException("Username already been used");
	}
}
