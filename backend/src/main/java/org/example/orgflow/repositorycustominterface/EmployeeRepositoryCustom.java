package org.example.orgflow.repositorycustominterface;

import org.example.orgflow.dto.charts.EmployeesPerDepartmentDTO;
import org.example.orgflow.dto.charts.EmployeesByJobTitleDTO;

import java.util.List;
public interface EmployeeRepositoryCustom {

    List<EmployeesPerDepartmentDTO> countEmployeesByDepartment();

    List<EmployeesByJobTitleDTO> countEmployeesByJobTitle();


}
