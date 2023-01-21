package com.example.rubentest.Controllers;

import com.example.rubentest.Models.Department;
import com.example.rubentest.Models.Employee;
import com.example.rubentest.Services.DepartmentService;
import com.example.rubentest.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/API/V1")
public class MainController {

    DepartmentService departmentService;

    EmployeeService employeeService;

    @Autowired
    public MainController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getListEmployees() {
        return employeeService.allEmployees();
    }

    @GetMapping("/employees/{searchWord}")
    public Employee getEmployeeSearch(@PathVariable String searchWord) {
        if(employeeService.employeesSearch(searchWord)!=null) {
            return employeeService.employeesSearch(searchWord);
        } else {
            System.out.println("No such employee");
            return null;
        }
    }

    @GetMapping("/dep-emp")
    public List<Department> getListDepartmentEmployee() {
        return departmentService.allDepartmentEmployees();
    }

    @GetMapping("/departmentName")
    public ResponseEntity departmentNamesDTO() {
        return departmentService.departmentListDTOResponse();
    }

    @PatchMapping("/{departmentId}/set-manager/{employeeId}")
    public void setManagerDepartment(@PathVariable Long departmentId, @PathVariable Long employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);
        Department department = departmentService.findDepartmentById(departmentId);
        if(Objects.equals(employee.getPosition(),"Manager")) {
            departmentService.setDepartmentManager(departmentId, employeeId);
            System.out.println("Manager of Department " + department.getName() + " changed to " + employee.getFullName());
        } else {
            System.out.println("Employee " + employee.getFullName() + " is not a manager");
        }
    }

    @DeleteMapping("/department/{departmentId}")
    public void deleteDepartment(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        System.out.println("Department " + departmentId + " has been deleted, all its employees' department fields are now null");
    }

}
