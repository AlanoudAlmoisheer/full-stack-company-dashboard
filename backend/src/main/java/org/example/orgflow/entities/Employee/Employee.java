package org.example.orgflow.entities.Employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.orgflow.entities.Department.Department;
import org.example.orgflow.entities.Manager.Manager;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name;

    private String jobTitle;

    @Column (unique = true)
    private String email;

    @JsonIgnore
    private Double salary;

    private LocalDate hireDate;
    @ManyToOne
    @JoinColumn (name = "department_id")
    private Department department;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;




}
