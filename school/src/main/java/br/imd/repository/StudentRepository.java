package br.imd.repository;


import org.springframework.stereotype.Repository;

import br.imd.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	
}