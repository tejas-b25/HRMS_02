package com.example.User.repository;

import com.example.User.entity.EmployeeTaxDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeTaxDetailsRepository extends JpaRepository<EmployeeTaxDetails, Long> {

    List<EmployeeTaxDetails> findByEmployeeId(Long employeeId);

    Optional<EmployeeTaxDetails> findByEmployeeIdAndTaxRegime(Long employeeId, EmployeeTaxDetails.TaxRegime taxRegime);
}
