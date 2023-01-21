package com.example.rubentest.Repositories;

import com.example.rubentest.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findEmployeeByFullName(String searchWord);

    Employee findEmployeeByEmail(String searchWord);
}
