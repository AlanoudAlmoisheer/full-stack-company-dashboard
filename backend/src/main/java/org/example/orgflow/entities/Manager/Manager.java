package org.example.orgflow.entities.Manager;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.orgflow.entities.Department.Department;
import org.example.orgflow.entities.Employee.Employee;


import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Manager {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @Column (unique = true)
    private String email;

    private Double salary;

    private LocalDate hireDate;

    @OneToMany (mappedBy = "manager")
    private List<Department> departments;

    @JsonManagedReference
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees;

}
