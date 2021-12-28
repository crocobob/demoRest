package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Sessions;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.InvalidCredentialException;
import com.example.demo.exceptions.SessionNotfoundException;
import com.example.demo.exceptions.UserDoesNotExistException;
import com.example.demo.repositories.SessionRepository;
import com.example.demo.repositories.UsersRepository;

@Service
public class SessionServiceImpl implements SessionService{
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public Sessions login(String username, String password) throws InvalidCredentialException, SessionNotfoundException {
		Users user = usersRepository.getLogin(username,password);
		if(user != null) {
			
			try {
				getSession(username);
				sessionRepository.updateSession(username);

				return getSession(username);
				
			} catch (SessionNotfoundException e) {
				sessionRepository.addSession(username);

				return getSession(username);
			}
		}
		else
			throw new InvalidCredentialException("Invalid Username or Password");
		
	}
	@Override
	public Sessions getSession(String username) throws SessionNotfoundException {
		return sessionRepository.findById(username).orElseThrow(() -> new SessionNotfoundException("Session not found with: "+username));
	}
	
	@Override
	public Sessions checker(String username) throws UserDoesNotExistException, SessionNotfoundException {

		if(sessionRepository.checker(username)!= null) {
			Sessions user = getSession(username);
			return user;
		}
		else
			throw new UserDoesNotExistException("User "+username+" is not Logged in or session has expired");
	}
	@Override
	public void logout(String username) throws SessionNotfoundException {
		Sessions session = sessionRepository.findById(username).orElseThrow(() -> new SessionNotfoundException(username + " is not logged in"));
		sessionRepository.delete(session);
	}

}
