package org.example.orgflow.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.orgflow.dto.DepartmentDTO;
import org.example.orgflow.mappers.DepartmentMapper;
import org.example.orgflow.entities.Department.Department;
import org.example.orgflow.entities.Employee.Employee;
import org.example.orgflow.entities.Employee.QEmployee;
import org.example.orgflow.entities.Manager.Manager;
import org.example.orgflow.response.PageResponse;
import org.example.orgflow.repositories.DepartmentRepository;
import org.example.orgflow.repositories.ManagerRepository;
import org.example.orgflow.errormessages.MessageConstants;
import org.example.orgflow.exceptions.model.BusinessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final ManagerRepository managerRepository;
    private final JPAQueryFactory queryFactory;
//-------------------------------------------------Create----------------------------------------


    @Transactional
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {

        //handle duplicate department
        if (departmentRepository.existsByDepartmentNameIgnoreCaseAndLocationIgnoreCase(
                departmentDTO.getDepartmentName(),
                departmentDTO.getLocation()
        )) {
            throw new BusinessException(MessageConstants.DEPARTMENT_ALREADY_EXISTS);
        }
        Department department = departmentMapper.toEntity(departmentDTO);

        if (departmentDTO.getManagerId() != null) { Manager manager = managerRepository.findById(departmentDTO.getManagerId())
                .orElseThrow(() -> new BusinessException(MessageConstants.DEPARTMENT_NOT_FOUND));
            //set the relationship
            department.setManager(manager); }
        else { department.setManager(null); }
        Department savedDepartment = departmentRepository.save(department);
        return departmentMapper.toDTO(savedDepartment); }

//------------------------------------------------------------Update----------------------------------------
@Transactional
public DepartmentDTO updateDepartment(long id, DepartmentDTO updateDTO) {

        //check if the department exists
    Department department = departmentRepository.findById(id)
            .orElseThrow(() ->
                    new BusinessException(MessageConstants.DEPARTMENT_NOT_FOUND)
            );

            String departmentName =
                     updateDTO.getDepartmentName() != null
                    ? updateDTO.getDepartmentName()
                    : department.getDepartmentName();

             String location =
                    updateDTO.getLocation() != null
                    ? updateDTO.getLocation()
                    : department.getLocation();


    //Update fields
    if (updateDTO.getDepartmentName() != null)
        department.setDepartmentName(updateDTO.getDepartmentName());

    if (updateDTO.getLocation() != null)
        department.setLocation(updateDTO.getLocation());

    //check if the manager exists
    if (updateDTO.getManagerId() != null) {
        Manager manager = managerRepository.findById(updateDTO.getManagerId())
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.MANAGER_NOT_FOUND));

        department.setManager(manager);
    }

    Department savedDepartment = departmentRepository.save(department);
    return departmentMapper.toDTO(savedDepartment);
}
    //-----------------------------------------------Delete-----------------------------------------------
    @Transactional
    public DepartmentDTO deleteDepartment(long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MessageConstants.DEPARTMENT_NOT_FOUND));

        departmentRepository.delete(department);

        return departmentMapper.toDTO(department);
    }

    //---------------------------------------------------Display All-------------------------------------------------------------------
    public List<DepartmentDTO> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            throw new BusinessException(MessageConstants.NO_DEPARTMENTS_FOUND);
        }
        return departments.stream()
                .map(departmentMapper::toDTO)
                .toList();
    }

    //---------------------------------------------------Display by Id-------------------------------------------------------------------
    public DepartmentDTO getDepartmentById(long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDTO)
                .orElseThrow(() -> new BusinessException(MessageConstants.DEPARTMENT_NOT_FOUND));
    }

    //---------------------------------------------------Get Department Manager-------------------------------------------------------------------
    public Manager getDepartmentManager(Long id) {


        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.DEPARTMENT_NOT_FOUND));

        if (department.getManager() == null) {
            throw new BusinessException(MessageConstants.DEPARTMENT_MANAGER_NOT_ASSIGNED);
        }

        return department.getManager();
    }


    //---------------------------------------------------get employees by Department---------------------------------------------------------------------
    public List<Employee> getEmployeesByDepartment(Long departmentId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new BusinessException(MessageConstants.DEPARTMENT_NOT_FOUND));

        QEmployee employee = QEmployee.employee;

        List<Employee> employees = queryFactory
                .selectFrom(employee)
                .where(employee.department.id.eq(departmentId))
                .fetch();

        if (employees.isEmpty()) {
            throw new BusinessException(MessageConstants.NO_EMPLOYEES_IN_DEPARTMENT);
        }

        return employees;

    }

    //---------------------------------------------------API pagination Concept---------------------------------------------------------------------
    public PageResponse<DepartmentDTO> getDepartments(int page, int size) {


        if (page < 0) {
            throw new BusinessException(MessageConstants.INVALID_PAGE_NUMBER);
        }

        if (size <= 0) {
            throw new BusinessException(MessageConstants.INVALID_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Department> departmentPage = departmentRepository.findAll(pageable);

        if (departmentPage.isEmpty()) {
            throw new BusinessException(MessageConstants.NO_DEPARTMENTS_FOUND);
        }

        List<DepartmentDTO> departmentDTOs = departmentPage.getContent()
                .stream()
                .map(departmentMapper::toDTO)
                .toList();

        PageResponse<DepartmentDTO> response = new PageResponse<>();

        response.setContent(departmentDTOs);
        response.setPageNumber(departmentPage.getNumber());
        response.setPageSize(departmentPage.getSize());
        response.setTotalElements(departmentPage.getTotalElements());
        response.setTotalPages(departmentPage.getTotalPages());
        response.setLast(departmentPage.isLast());

        return response;
    }

}
