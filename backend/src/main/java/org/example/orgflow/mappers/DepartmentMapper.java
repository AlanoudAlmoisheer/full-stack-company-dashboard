package org.example.orgflow.mappers;


import org.example.orgflow.dto.DepartmentDTO;
import org.example.orgflow.entities.Department.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {



    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "manager.name", target = "managerName")
    @Mapping(source = "manager.email", target = "managerEmail")
    DepartmentDTO toDTO(Department department);



    @Mapping (target = "employees", ignore = true)
    @Mapping(target = "manager", ignore = true)
    Department toEntity(DepartmentDTO dto);

}
