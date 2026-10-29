package org.example.orgflow.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {


    private String name;
    private Double salary;
    private  Long id;
    private String jobTitle;
    private LocalDate hireDate;
    private String email;

    private Long departmentId;
    private String departmentName;

    private Long managerId;
    private String managerName;
    private String managerEmail;

    //input
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "This value is not allowed. Please enter a value greater than 0.")
    private Double percentage;
}
