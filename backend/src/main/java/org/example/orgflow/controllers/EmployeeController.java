package org.example.orgflow.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orgflow.dto.EmployeeDTO;
import org.example.orgflow.response.PageResponse;
import org.example.orgflow.services.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/Employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    //-----------------------------------------------Create Employee-----------------------------------------------
    @PostMapping("/add")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.addEmployee(employeeDTO);
    }

    //-----------------------------------------------Update employee info-----------------------------------------------
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeDTO employeeDTO) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, employeeDTO)
        );
    }
    //-------------------------------------------------Delete-------------------------------------------------
    @DeleteMapping("/delete/{id}")
    public EmployeeDTO deleteEmployee(@PathVariable Long id) {

        return employeeService.deleteEmployee(id);
    }

    //----------------------------------------------increase Employee Salary by Percentage-----------------------------------------------
    @PatchMapping("/{id}/increase-salary")
    public ResponseEntity<EmployeeDTO> increaseSalary(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO updatedEmployee =
                employeeService.increaseSalary(id, employeeDTO);

        return ResponseEntity.ok(updatedEmployee);
    }

    //---------------------------------------------------Display All-------------------------------------------------------------------
    @GetMapping("/all")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    //---------------------------------------------------Display by ID-------------------------------------------------------------------
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }


    //---------------------------------------------------Hired After------------------------------------------------------------------
    @GetMapping("/hired-after")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesHiredAfter(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        List<EmployeeDTO> employees =
                employeeService.findEmployeesHiredAfter(date);

        return ResponseEntity.ok(employees);
    }
    //---------------------------------------------------API pagination Concept---------------------------------------------------------------------
    @GetMapping("/employees")
    public PageResponse<EmployeeDTO> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        return employeeService.getEmployees(page, size);
    }
}





