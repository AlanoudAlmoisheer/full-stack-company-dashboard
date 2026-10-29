package org.example.orgflow.controllers.charts;

import lombok.AllArgsConstructor;
import org.example.orgflow.dto.charts.EmployeesByJobTitleDTO;
import org.example.orgflow.services.charts.EmployeesByJobTitleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class EmployeesByJobTitleController {

    private final EmployeesByJobTitleService service;

    @GetMapping("/employee-jobtitle-stats")
    public List<EmployeesByJobTitleDTO> getStats() {
        return service.getEmployeeJobTitleStats();
    }
}
