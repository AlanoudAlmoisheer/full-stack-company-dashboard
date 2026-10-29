package org.example.orgflow.mappers;


import org.example.orgflow.dto.EmployeeDTO;
import org.example.orgflow.entities.Employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

        @Mapping(source = "department.id", target = "departmentId")
        @Mapping(source = "department.departmentName", target = "departmentName")
        @Mapping(source = "manager.id", target = "managerId")
       @Mapping(source = "manager.name", target = "managerName")
       @Mapping(source = "manager.email", target = "managerEmail")
        @Mapping(target = "percentage", ignore = true)
        EmployeeDTO toDTO(Employee employee);


        @Mapping(target = "department", ignore = true)
         @Mapping(target = "manager", ignore = true)
        Employee toEntity(EmployeeDTO dto);

}
