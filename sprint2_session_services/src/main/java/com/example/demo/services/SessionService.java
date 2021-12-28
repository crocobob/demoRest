package com.example.demo.services;

import com.example.demo.entities.Sessions;
import com.example.demo.exceptions.InvalidCredentialException;
import com.example.demo.exceptions.SessionNotfoundException;
import com.example.demo.exceptions.UserDoesNotExistException;

public interface SessionService {
	Sessions login(String username, String password) throws InvalidCredentialException, SessionNotfoundException;

	Sessions getSession(String username) throws SessionNotfoundException;

	Sessions checker(String username) throws UserDoesNotExistException, SessionNotfoundException;

	void logout(String username) throws SessionNotfoundException;
}
