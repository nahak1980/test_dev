package com.learn.pdf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.learn.pdf.entity.User;

@Repository
@CrossOrigin
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findById(Long id);
}
