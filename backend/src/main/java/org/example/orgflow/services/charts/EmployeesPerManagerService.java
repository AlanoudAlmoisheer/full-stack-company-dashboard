package org.example.orgflow.services.charts;


import lombok.RequiredArgsConstructor;
import org.example.orgflow.entities.Employee.Employee;
import org.example.orgflow.dto.charts.EmployeesPerManagerDTO;
import org.example.orgflow.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeesPerManagerService {


    private final EmployeeRepository employeeRepository;



    public List<EmployeesPerManagerDTO> getManagerEmployeeCounts() {
        List<Employee> employees = employeeRepository.findAll();


        Map<String, Long> counts = employees.stream()
                .filter(e -> e.getManager() != null)
                .collect(Collectors.groupingBy(
                        e -> e.getManager().getName(),
                        Collectors.counting()
                ));

        return counts.entrySet().stream()
                .map(entry -> new EmployeesPerManagerDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
