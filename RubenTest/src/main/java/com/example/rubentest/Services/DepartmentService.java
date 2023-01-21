package com.example.rubentest.Services;

import com.example.rubentest.Models.Department;
import com.example.rubentest.Models.DepartmentListDTOResponse;
import com.example.rubentest.Models.Employee;
import com.example.rubentest.Repositories.DepartmentRepository;
import com.example.rubentest.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    public DepartmentRepository departmentRepository;

    public EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Department> allDepartmentEmployees() {
        return departmentRepository.findAll();
    }

    public Department findDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).get();
    }

    public ResponseEntity departmentListDTOResponse() {

        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentListDTOResponse> departmentListDTOResponses = new ArrayList<>();

        for(Department department : departmentList) {
            DepartmentListDTOResponse departmentListDTOResponse = new DepartmentListDTOResponse();
            departmentListDTOResponse.setDepartmentId(department.getId());
            departmentListDTOResponse.setDepartmentName(department.getName());

            departmentListDTOResponses.add(departmentListDTOResponse);
        }

        return ResponseEntity.ok(departmentListDTOResponses);
    }

    public void setDepartmentManager(Long departmentId, Long employeeId) {
        Department department = departmentRepository.findById(departmentId).get();
        department.setManager_id(employeeId);

        departmentRepository.save(department);
    }

    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).get();

        for(Employee employee : department.getListEmployees()) {
            Employee nullDepartment = employee;
            nullDepartment.setDepartment_id(null);

            employeeRepository.save(nullDepartment);
        }
        departmentRepository.deleteById(departmentId);
    }
}
