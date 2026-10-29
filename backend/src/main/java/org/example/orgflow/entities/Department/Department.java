package org.example.orgflow.entities.Department;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.orgflow.entities.Employee.Employee;
import org.example.orgflow.entities.Manager.Manager;

import java.util.List;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"departmentName", "location"}
                )
        }
)

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentName;

    private String location;

    @OneToMany(mappedBy = "department")

    private List<Employee> employees;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id" )
    private Manager manager;



}
