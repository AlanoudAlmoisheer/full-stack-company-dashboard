package org.example.orgflow.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDTO {

    //output
    @NotBlank(message = "Name is required !")
    private String name;
    @NotBlank(message = "Email is required")
    private String email;
    private Double salary;
    private LocalDate hireDate;
    private Long id;

    private Long departmentId;
    private String departmentName;

    //input
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "This value is not allowed. Please enter a value greater than 0.")
    private Double percentage;
}
