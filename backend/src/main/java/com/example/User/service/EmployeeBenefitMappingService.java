package com.example.User.service;

import com.example.User.dto.EmployeeBenefitMappingDTO;
import com.example.User.entity.EmployeeBenefitMapping;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EmployeeBenefitMappingService {


    EmployeeBenefitMapping createMapping(EmployeeBenefitMapping mapping);

    EmployeeBenefitMapping updateMapping(Long id, EmployeeBenefitMapping mapping);

    void deleteMapping(Long id);

    EmployeeBenefitMapping getMappingById(Long id);

    List<EmployeeBenefitMapping> getAllMappings();

    List<EmployeeBenefitMapping> getMappingsByEmployeeId(Long employeeId);
}




