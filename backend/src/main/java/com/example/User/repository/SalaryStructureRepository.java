package com.example.User.repository;
import com.example.User.entity.SalaryStructureMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryStructureRepository extends JpaRepository<SalaryStructureMaster, Long> {
    Optional<SalaryStructureMaster> findByStructureName(String name);
}
