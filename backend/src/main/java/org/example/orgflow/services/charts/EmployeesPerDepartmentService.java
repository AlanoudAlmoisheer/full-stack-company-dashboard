package org.example.orgflow.services.charts;

import lombok.AllArgsConstructor;
import org.example.orgflow.dto.charts.EmployeesPerDepartmentDTO;
import org.example.orgflow.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EmployeesPerDepartmentService {

   private final EmployeeRepository employeeRepository;

   public List<EmployeesPerDepartmentDTO> getEmployeeStats() {
       return employeeRepository.countEmployeesByDepartment();
    }

}
