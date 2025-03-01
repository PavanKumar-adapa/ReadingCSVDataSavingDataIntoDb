package com.csvData.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csvData.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

}
