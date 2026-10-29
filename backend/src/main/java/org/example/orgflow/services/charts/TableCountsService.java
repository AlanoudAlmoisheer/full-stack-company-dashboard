package org.example.orgflow.services.charts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.orgflow.repositories.EmployeeRepository;
import org.example.orgflow.repositories.DepartmentRepository;
import org.example.orgflow.repositories.ManagerRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class TableCountsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public Map<String, Long> getCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("employees", employeeRepository.count());
        counts.put("departments", departmentRepository.count());
        counts.put("managers", managerRepository.count());
        return counts;
    }
}
