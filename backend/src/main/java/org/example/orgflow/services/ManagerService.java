package org.example.orgflow.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.orgflow.entities.Manager.Manager;
import org.example.orgflow.dto.ManagerDTO;
import org.example.orgflow.mappers.ManagerMapper;
import org.example.orgflow.response.PageResponse;
import org.example.orgflow.repositories.ManagerRepository;
import org.example.orgflow.repositories.EmployeeRepository;
import org.example.orgflow.repositories.DepartmentRepository;
import org.example.orgflow.errormessages.MessageConstants;
import org.example.orgflow.exceptions.model.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;


    //-----------------------------------------------create-----------------------------------------------
    public ManagerDTO addManager(ManagerDTO managerDTO) {

        //handle duplicate Manager
        if (managerRepository.existsByEmailIgnoreCase(
                managerDTO.getEmail()
        )) {
            throw new BusinessException(MessageConstants.MANAGER_ALREADY_EXISTS);
        }

        // step 1: convert DTO → Entity
        Manager manager = managerMapper.toEntity(managerDTO);

        // step 2: save
        Manager savedManager = managerRepository.save(manager);

        //step 3 : convert back Entity → DTO
        return managerMapper.toDTO(savedManager);
    }

    //-----------------------------------------------Update Manager info-----------------------------------------------
    @Transactional
    public ManagerDTO updateManager(Long id, ManagerDTO updateDTO) {

        // check if manager exists
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.MANAGER_NOT_FOUND)
                );

        String email =
                updateDTO.getEmail() != null
                        ? updateDTO.getEmail()
                        : manager.getEmail();



        // Update fields
        if (updateDTO.getName() != null)
            manager.setName(updateDTO.getName());

        if (updateDTO.getEmail() != null)
            manager.setEmail(updateDTO.getEmail());

        if (updateDTO.getSalary() != null)
            manager.setSalary(updateDTO.getSalary());

        Manager savedManager = managerRepository.save(manager);

        return managerMapper.toDTO(savedManager);
    }

    //-----------------------------------------------Delete-----------------------------------------------
    public ManagerDTO deleteManager(Long id) {

        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MessageConstants.MANAGER_NOT_FOUND));

        boolean hasDepartmentDependency = departmentRepository.findAll()
                .stream()
                .anyMatch(department ->
                        department.getManager() != null &&
                                department.getManager().getId().equals(id)
                );

        boolean hasEmployeeDependency = employeeRepository.findAll()
                .stream()
                .anyMatch(employee ->
                        employee.getManager() != null &&
                                employee.getManager().getId().equals(id)
                );

        if (hasDepartmentDependency && hasEmployeeDependency) {
            throw new BusinessException(MessageConstants.MANAGER_DEPENDENCY_BOTH);
        }

        if (hasDepartmentDependency) {
            throw new BusinessException(MessageConstants.MANAGER_DEPENDENCY_DEPARTMENT);
        }

        if (hasEmployeeDependency) {
            throw new BusinessException(MessageConstants.MANAGER_DEPENDENCY_EMPLOYEE);
        }

        managerRepository.delete(manager);

        return managerMapper.toDTO(manager);
    }
    //----------------------------------------------increase Manager Salary by Percentage-----------------------------------------------
    @Transactional
    public ManagerDTO increaseSalary(Long id, ManagerDTO managerDTO) {


        Manager manager = managerRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.MANAGER_NOT_FOUND)
                );


        if (managerDTO.getPercentage() == null) {
            throw new BusinessException(MessageConstants.SALARY_PERCENTAGE_REQUIRED);
        }

        double percentage = managerDTO.getPercentage();

        if (percentage <= 0) {
            throw new BusinessException(MessageConstants.INVALID_SALARY_PERCENTAGE);
        }

        double updatedSalary = manager.getSalary() * (1 + percentage / 100);
        manager.setSalary(updatedSalary);

        Manager savedManager = managerRepository.save(manager);

        return managerMapper.toDTO(savedManager);
    }
    //---------------------------------------------------Display All-------------------------------------------------------------------
    public List<ManagerDTO> getAllManagers() {

        List<Manager> managers = managerRepository.findAll();
        if (managers.isEmpty()) {
            throw new BusinessException(MessageConstants.NO_MANAGERS_FOUND);
        }
        return managers.stream()
                .map(managerMapper::toDTO)
                .toList();
    }


    //---------------------------------------------------Display by ID---------------------------------------------------------------------

    public ManagerDTO getManagerById(Long id) {
        return managerRepository.findById(id)
                .map(managerMapper::toDTO)
                .orElseThrow(() -> new BusinessException(MessageConstants.MANAGER_NOT_FOUND));
    }


    //---------------------------------------------------API pagination Concept---------------------------------------------------------------------
    public PageResponse<ManagerDTO> getManagers(int page, int size) {

        if (page < 0) {
            throw new BusinessException(MessageConstants.INVALID_PAGE_NUMBER);
        }

        if (size <= 0) {
            throw new BusinessException(MessageConstants.INVALID_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Manager> managerPage = managerRepository.findAll(pageable);

        if (managerPage.isEmpty()) {
            throw new BusinessException(MessageConstants.NO_MANAGERS_FOUND);
        }

        List<ManagerDTO> managerDTOs = managerPage.getContent()
                .stream()
                .map(managerMapper::toDTO)
                .toList();

        PageResponse<ManagerDTO> response = new PageResponse<>();

        response.setContent(managerDTOs);
        response.setPageNumber(managerPage.getNumber());
        response.setPageSize(managerPage.getSize());
        response.setTotalElements(managerPage.getTotalElements());
        response.setTotalPages(managerPage.getTotalPages());
        response.setLast(managerPage.isLast());

        return response;
    }
}











