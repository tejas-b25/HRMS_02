package com.example.User.repository;

import com.example.User.entity.EmployeeBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeBankDetailsRepository extends JpaRepository<EmployeeBankDetails, Long> {
    Optional<EmployeeBankDetails> findByEmployeeIdAndActiveTrue(Long employeeId);
    boolean existsByEmployeeId(Long employeeId);
    Optional<EmployeeBankDetails> findByEmployeeBankDetailsIdAndActiveTrue(Long id);
    boolean existsByEmployee_EmployeeId(Long employeeId);
}

