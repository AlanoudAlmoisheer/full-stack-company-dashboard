package org.example.orgflow.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orgflow.dto.DepartmentDTO;
import org.example.orgflow.entities.Manager.Manager;
import org.example.orgflow.response.PageResponse;
import org.example.orgflow.services.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.orgflow.entities.Employee.Employee;
import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/Department")
@RequiredArgsConstructor

public class DepartmentController {

    private final DepartmentService departmentService;

    //-----------------------------------------------Create Department-----------------------------------------------
    @PostMapping("/add")
    public DepartmentDTO addDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) {
        return departmentService.addDepartment(departmentDTO);
    }

    //-----------------------------------------------Update Department info-----------------------------------------------
    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentDTO departmentDTO) {

        DepartmentDTO updatedDepartment =
                departmentService.updateDepartment(id, departmentDTO);

        return ResponseEntity.ok(updatedDepartment);
    }

    //-----------------------------------------------Delete-----------------------------------------------
    @DeleteMapping("/delete/{id}")
    public DepartmentDTO deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }

    //---------------------------------------------------Display All-------------------------------------------------------------------
    @GetMapping("/all")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    //---------------------------------------------------Display by ID-------------------------------------------------------------------
    @GetMapping("/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }


    //---------------------------------------------------Display Department Manager-------------------------------------------------------------------
    @GetMapping("/{id}/manager")
    public Manager getDepartmentManager(@PathVariable Long id) {
        return departmentService.getDepartmentManager(id);
    }

    //---------------------------------------------------Display Departments with its employees--------------------------------------------------------

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByDepartment(@PathVariable("id") Long departmentId) {
        return departmentService.getEmployeesByDepartment(departmentId);
    }

    //---------------------------------------------------API pagination Concept---------------------------------------------------------------------
    @GetMapping("/departments")
    public PageResponse<DepartmentDTO> getDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {


        return departmentService.getDepartments(page, size);
    }

}
