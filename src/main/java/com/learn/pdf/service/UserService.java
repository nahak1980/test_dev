package com.learn.pdf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.pdf.entity.User;
import com.learn.pdf.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}

	public User addUser(User user) {
		return userRepository.save(user);
	}
	
	public String deleteStudent(long id) {
		userRepository.deleteById(id);
		return "Student has been deleted successfully";
	}

	public Optional<User> findUser(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser;
	}
	
}
