package com.learn.pdf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.pdf.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
