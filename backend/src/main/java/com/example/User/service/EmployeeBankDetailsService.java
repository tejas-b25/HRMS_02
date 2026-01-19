package com.example.User.service;

import com.example.User.dto.EmployeeBankDetailsRequestDto;
import com.example.User.dto.EmployeeBankDetailsResponseDto;

public interface EmployeeBankDetailsService {

    EmployeeBankDetailsResponseDto createBankDetails(EmployeeBankDetailsRequestDto dto);

    EmployeeBankDetailsResponseDto getBankDetailsById(Long bankId);

    EmployeeBankDetailsResponseDto getBankDetailsByEmployeeId(Long employeeId);

    EmployeeBankDetailsResponseDto updateBankDetails(Long bankId, EmployeeBankDetailsRequestDto dto);

    String deleteBankDetails(Long bankId);
    String hardDeleteBankDetails(Long bankId);

}

