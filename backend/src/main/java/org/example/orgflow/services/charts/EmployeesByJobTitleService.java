package org.example.orgflow.services.charts;

import lombok.AllArgsConstructor;
import org.example.orgflow.dto.charts.EmployeesByJobTitleDTO;
import org.example.orgflow.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EmployeesByJobTitleService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeesByJobTitleDTO> getEmployeeJobTitleStats() {
        return employeeRepository.countEmployeesByJobTitle();
    }


}
