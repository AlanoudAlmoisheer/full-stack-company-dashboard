package org.example.orgflow.repositories;


import org.example.orgflow.entities.Manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    boolean existsByEmailIgnoreCase(String email);


    Optional<Manager> findById(Long id);
}
