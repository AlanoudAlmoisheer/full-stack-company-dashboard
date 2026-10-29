package org.example.orgflow.repositories;

import org.example.orgflow.repositorycustominterface.EmployeeRepositoryCustom;
import org.example.orgflow.entities.Employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom, JpaSpecificationExecutor<Employee> {

    boolean existsByEmailIgnoreCase(String email);


}
