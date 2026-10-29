package org.example.orgflow.dto.charts;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesPerDepartmentDTO {

    private String departmentName;
    private Long employeeCount;

}
