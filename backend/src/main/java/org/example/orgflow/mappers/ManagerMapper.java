package org.example.orgflow.mappers;


import org.example.orgflow.dto.ManagerDTO;
import org.example.orgflow.entities.Manager.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerMapper {

    @Mapping(target = "percentage", ignore = true)
    ManagerDTO toDTO(Manager manager);


    @Mapping(target = "departments", ignore = true)

    Manager toEntity(ManagerDTO managerDTO);

}






