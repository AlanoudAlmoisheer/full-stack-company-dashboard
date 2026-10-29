package org.example.orgflow.controllers.charts;

import lombok.AllArgsConstructor;
import org.example.orgflow.dto.charts.EmployeesPerManagerDTO;
import org.example.orgflow.services.charts.EmployeesPerManagerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class EmployeesPerManagerController {

    private final EmployeesPerManagerService managerEmployeeService;


    @GetMapping("/manager-employee-pie")
    public List<EmployeesPerManagerDTO> getManagerEmployeePieData() {
        return managerEmployeeService.getManagerEmployeeCounts();
    }
}
