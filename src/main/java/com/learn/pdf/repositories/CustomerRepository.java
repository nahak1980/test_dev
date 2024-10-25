package com.learn.pdf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learn.pdf.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	@Query(value = "SELECT * FROM customers WHERE id = :id", nativeQuery = true)
    public Optional<Customer> findById(@Param("id") Long id);
	
	@Query("SELECT c FROM Customer c WHERE c.id = :id")
	public Optional<Customer> checkFindById(@Param("id") Long id);
	
}
