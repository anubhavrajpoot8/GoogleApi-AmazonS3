package com.assignment.FreightFox.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.FreightFox.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}