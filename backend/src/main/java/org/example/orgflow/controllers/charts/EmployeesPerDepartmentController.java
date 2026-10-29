package org.example.orgflow.controllers.charts;


import lombok.AllArgsConstructor;
import org.example.orgflow.dto.charts.EmployeesPerDepartmentDTO;
import org.example.orgflow.services.charts.EmployeesPerDepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class EmployeesPerDepartmentController {

    private final EmployeesPerDepartmentService service;

   @GetMapping("/employee-department-count")
   public List<EmployeesPerDepartmentDTO> getStats() {
       return service.getEmployeeStats();
   }
}
