package org.example.orgflow.dto.charts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeesPerManagerDTO {

    private String managerName;
    private Long employeeCount;
}
