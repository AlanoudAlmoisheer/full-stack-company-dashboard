package org.example.orgflow.repositories;


import org.example.orgflow.entities.Department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {

    boolean existsByDepartmentNameIgnoreCaseAndLocationIgnoreCase(
            String departmentName,
            String location
    );
}
