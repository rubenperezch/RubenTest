package com.example.rubentest.Services;

import com.example.rubentest.Models.Employee;
import com.example.rubentest.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    public EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> allEmployees() {
        return employeeRepository.findAll();
    }

    public Employee employeesSearch(String searchWord) {
        if(employeeRepository.findEmployeeByFullName(searchWord)!=null) {
            return employeeRepository.findEmployeeByFullName(searchWord);
        } else if(employeeRepository.findEmployeeByFullName(searchWord)==null && employeeRepository.findEmployeeByEmail(searchWord)!=null) {
            return employeeRepository.findEmployeeByEmail(searchWord);
        } else {
            return null;
        }
    }

    public Employee findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }
}
