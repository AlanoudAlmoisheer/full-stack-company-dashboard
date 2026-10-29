package org.example.orgflow.services;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.orgflow.dto.EmployeeDTO;
import org.example.orgflow.mappers.EmployeeMapper;
import org.example.orgflow.entities.Department.Department;
import org.example.orgflow.entities.Employee.Employee;
import org.example.orgflow.entities.Employee.QEmployee;
import org.example.orgflow.entities.Manager.Manager;
import org.example.orgflow.response.PageResponse;
import org.example.orgflow.repositories.DepartmentRepository;
import org.example.orgflow.repositories.EmployeeRepository;
import org.example.orgflow.repositories.ManagerRepository;
import org.example.orgflow.errormessages.MessageConstants;
import org.example.orgflow.exceptions.model.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository;
    private final JPAQueryFactory queryFactory;
    private final ManagerRepository managerRepository;

    //-----------------------------------------------create-----------------------------------------------

    @Transactional
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {


        if (employeeRepository.existsByEmailIgnoreCase(employeeDTO.getEmail())) {
            throw new BusinessException(MessageConstants.EMPLOYEE_ALREADY_EXISTS);
        }


        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.DEPARTMENT_NOT_FOUND)
                );


        Employee employee = employeeMapper.toEntity(employeeDTO);


        employee.setDepartment(department);


        if (employeeDTO.getManagerId() != null) {

            Manager manager = managerRepository.findById(employeeDTO.getManagerId())
                    .orElseThrow(() ->
                            new BusinessException(MessageConstants.MANAGER_NOT_FOUND)
                    );


            boolean managerBelongsToDepartment = manager.getDepartments()
                    .stream()
                    .anyMatch(d -> d.getId().equals(department.getId()));

            if (!managerBelongsToDepartment) {
                throw new BusinessException(
                        MessageConstants.MANAGER_DIFFERENT_DEPARTMENT
                );
            }

            employee.setManager(manager);
        }


        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toDTO(savedEmployee);
    }
//-----------------------------------------------Update Employee info-----------------------------------------------
@Transactional
public EmployeeDTO updateEmployee(Long id, EmployeeDTO updateDTO) {


    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() ->
                    new BusinessException(MessageConstants.EMPLOYEE_NOT_FOUND)
            );

          String email =
            updateDTO.getEmail() != null
                    ? updateDTO.getEmail()
                    : employee.getEmail();


    // Update fields
    if (updateDTO.getName() != null)
        employee.setName(updateDTO.getName());

    if (updateDTO.getEmail() != null)
        employee.setEmail(updateDTO.getEmail());

    if (updateDTO.getSalary() != null)
        employee.setSalary(updateDTO.getSalary());

    if (updateDTO.getJobTitle() != null)
        employee.setJobTitle(updateDTO.getJobTitle());

    if (updateDTO.getHireDate() != null)
        employee.setHireDate(updateDTO.getHireDate());

    // Update department
    if (updateDTO.getDepartmentId() != null) {
        Department department = departmentRepository.findById(updateDTO.getDepartmentId())
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.DEPARTMENT_NOT_FOUND)
                );
        employee.setDepartment(department);
    }


    if (updateDTO.getManagerId() != null) {

        Manager manager = managerRepository.findById(updateDTO.getManagerId())
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.MANAGER_NOT_FOUND)
                );


        if (employee.getDepartment() != null &&
                !manager.getDepartments().contains(employee.getDepartment())) {

            throw new BusinessException(
                    MessageConstants.MANAGER_DIFFERENT_DEPARTMENT
            );
        }

        employee.setManager(manager);
    }

    Employee savedEmployee = employeeRepository.save(employee);

    return employeeMapper.toDTO(savedEmployee);

}

    //-----------------------------------------------Delete---------------------------------------

    public EmployeeDTO deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MessageConstants.EMPLOYEE_NOT_FOUND));

        employeeRepository.delete(employee);

        return employeeMapper.toDTO(employee);
    }
// --------------------------------Increase Salary By percentage---------------------------------

    @Transactional
    public EmployeeDTO increaseSalary(Long id, EmployeeDTO employeeDTO) {

        // Check if employee exists
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.EMPLOYEE_NOT_FOUND)
                );

        // Validate percentage
        if (employeeDTO.getPercentage() == null) {
            throw new BusinessException(MessageConstants.SALARY_PERCENTAGE_REQUIRED);
        }

        double percentage = employeeDTO.getPercentage();

        if (percentage <= 0) {
            throw new BusinessException(MessageConstants.INVALID_SALARY_PERCENTAGE);
        }

        // Calculate new salary
        double updatedSalary = employee.getSalary() * (1 + percentage / 100);
        employee.setSalary(updatedSalary);

        // Save updated employee
        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toDTO(savedEmployee);
    }

    //---------------------------------------------------Display All-------------------------------------------------------------------
    public List<EmployeeDTO> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new BusinessException(MessageConstants.NO_EMPLOYEES_FOUND);
        }
        return employees.stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    //---------------------------------------------------Display by ID---------------------------------------------------------------------
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDTO)
                .orElseThrow(() -> new BusinessException(MessageConstants.EMPLOYEE_NOT_FOUND));
    }

    //---------------------------------------------------Hired After------------------------------------------------------------------
    public List<EmployeeDTO> findEmployeesHiredAfter(LocalDate date) {

        // Validate date
        if (date == null) {
            throw new BusinessException(
                    MessageConstants.HIRE_DATE_REQUIRED);
        }

        QEmployee employee = QEmployee.employee;

        List<Employee> employees = queryFactory
                .selectFrom(employee)
                .where(employee.hireDate.after(date))
                .fetch();

        // Handle empty result
        if (employees.isEmpty()) {
            throw new BusinessException(
                    MessageConstants.NO_EMPLOYEES_FOUND_AFTER_DATE);
        }

        return employees.stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    //---------------------------------------------------API pagination Concept---------------------------------------------------------------------
    public PageResponse<EmployeeDTO> getEmployees(int page, int size) {

        // Validate page
        if (page < 0) {
            throw new BusinessException(MessageConstants.INVALID_PAGE_NUMBER);
        }

        // Validate size
        if (size <= 0) {
            throw new BusinessException(MessageConstants.INVALID_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        // Handle empty result
        if (employeePage.isEmpty()) {
            throw new BusinessException(MessageConstants.NO_EMPLOYEES_FOUND);
        }

        List<EmployeeDTO> employeeDTOs = employeePage.getContent()
                .stream()
                .map(employeeMapper::toDTO)
                .toList();

        PageResponse<EmployeeDTO> response = new PageResponse<>();

        response.setContent(employeeDTOs);
        response.setPageNumber(employeePage.getNumber());
        response.setPageSize(employeePage.getSize());
        response.setTotalElements(employeePage.getTotalElements());
        response.setTotalPages(employeePage.getTotalPages());
        response.setLast(employeePage.isLast());

        return response;
    }


}








