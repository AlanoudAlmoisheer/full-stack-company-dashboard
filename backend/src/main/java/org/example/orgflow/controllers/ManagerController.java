package org.example.orgflow.controllers;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orgflow.dto.ManagerDTO;
import org.example.orgflow.response.PageResponse;
import org.example.orgflow.services.ManagerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/Manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;


    //-----------------------------------------------Create Manager-----------------------------------------------
    @PostMapping("/add")
    public ManagerDTO addManager( @RequestBody ManagerDTO managerDTO) {
        return managerService.addManager(managerDTO);
    }


    //-----------------------------------------------Update Manager info-----------------------------------------------
    @PutMapping("/update/{id}")
    public ResponseEntity<ManagerDTO> updateManager(
            @PathVariable Long id,
            @RequestBody @Valid ManagerDTO managerDTO) {

        ManagerDTO updatedManager = managerService.updateManager(id, managerDTO);
        return ResponseEntity.ok(updatedManager);
    }
    //-----------------------------------------------Delete-----------------------------------------------

    @DeleteMapping("/delete/{id}")
    public ManagerDTO deleteManager(@PathVariable Long id) {
        return managerService.deleteManager(id);

    }

    //---------------------------------------------------Display All-------------------------------------------------------------------
    @GetMapping("/all")
    public List<ManagerDTO> getAllManagers() {
        return managerService.getAllManagers();
    }

    //---------------------------------------------------Display by ID-------------------------------------------------------------------

    @GetMapping("/{id}")
    public ManagerDTO getManagerById(@PathVariable Long id) {
        return managerService.getManagerById(id);
    }

    //----------------------------------------------increase Manager Salary by Percentage-----------------------------------------------
    @PatchMapping("/{id}/increase-salary")
    public ResponseEntity<ManagerDTO> increaseSalary(
            @PathVariable Long id,
            @Valid @RequestBody ManagerDTO managerDTO) {


        return ResponseEntity.ok(
                //call service method
                managerService.increaseSalary(id, managerDTO)
        );
    }

    //---------------------------------------------------API pagination Concept------------------------------------------------------------
    @GetMapping("/managers")
    public PageResponse<ManagerDTO> getManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        return managerService.getManagers(page, size);
    }

    }



