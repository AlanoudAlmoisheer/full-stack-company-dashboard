package org.example.orgflow.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {



    private Long id;

    @NotBlank(message = "Department name is required")
    private String departmentName;


    @NotBlank(message = "Location is required")
    private String location;

    private Long managerId;
    private String managerName;
    private String managerEmail;
}
