package org.example.orgflow.repositories;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.orgflow.dto.charts.EmployeesPerDepartmentDTO;
import org.example.orgflow.dto.charts.EmployeesByJobTitleDTO;
import org.example.orgflow.entities.Employee.QEmployee;
import org.example.orgflow.entities.Department.QDepartment;
import org.example.orgflow.repositorycustominterface.EmployeeRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.orgflow.entities.Employee.QEmployee.employee;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<EmployeesPerDepartmentDTO> countEmployeesByDepartment() {

        QEmployee employee = QEmployee.employee;
        QDepartment department = QDepartment.department;

        return queryFactory
                .select(
                        com.querydsl.core.types.Projections.constructor(
                                EmployeesPerDepartmentDTO.class,
                                department.departmentName,
                                employee.id.count()
                        )
                )
                .from(employee)
                .join(employee.department, department)
                .groupBy(department.departmentName)
                .fetch();
    }

    @Override
    public List<EmployeesByJobTitleDTO> countEmployeesByJobTitle() {

        return queryFactory
                .select(
                        Projections.constructor(
                                EmployeesByJobTitleDTO.class,
                                employee.jobTitle,
                                employee.id.count()
                        )
                )
                .from(employee)
                .groupBy(employee.jobTitle)
                .fetch();
    }
}
