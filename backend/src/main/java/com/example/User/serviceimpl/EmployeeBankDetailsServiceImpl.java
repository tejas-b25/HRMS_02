package com.example.User.serviceimpl;

import com.example.User.dto.EmployeeBankDetailsRequestDto;
import com.example.User.dto.EmployeeBankDetailsResponseDto;
import com.example.User.entity.Employee;
import com.example.User.entity.EmployeeBankDetails;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.EmployeeBankDetailsRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.service.EmployeeBankDetailsService;
import com.example.User.util.MaskUtil;
import com.example.User.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeBankDetailsServiceImpl implements EmployeeBankDetailsService {

    private final EmployeeBankDetailsRepository bankRepo;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;




    @Override
    public EmployeeBankDetailsResponseDto createBankDetails(EmployeeBankDetailsRequestDto dto) {

        validationUtil.validateIFSC(dto.getIfsc());
        validationUtil.validateAccountNumber(dto.getAccountNumber());
        validationUtil.validateUAN(dto.getUanNumber());

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (bankRepo.existsByEmployee_EmployeeId(dto.getEmployeeId())) {
            throw new RuntimeException("Bank details already exist for this employee");
        }

        EmployeeBankDetails bankDetails = EmployeeBankDetails.builder()
                .employee(employee)
                .bankName(dto.getBankName())
                .accountHolderName(dto.getAccountHolderName())
                .accountNumber(dto.getAccountNumber())
                .accountType(dto.getAccountType())
                .ifsc(dto.getIfsc())
                .branch(dto.getBranch())
                .uanNumber(dto.getUanNumber())
                .active(true)
                .build();

        bankRepo.save(bankDetails);

        return convertToResponse(bankDetails);

    }

    @Override
    public EmployeeBankDetailsResponseDto getBankDetailsById(Long bankId) {
        EmployeeBankDetails details = bankRepo.findByEmployeeBankDetailsIdAndActiveTrue(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank details not found"));

        return convertToResponse(details);
    }

    @Override
    public EmployeeBankDetailsResponseDto getBankDetailsByEmployeeId(Long employeeId) {
        EmployeeBankDetails details = bankRepo.findByEmployeeIdAndActiveTrue(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank details not found"));
        return convertToResponse(details);
    }

    @Override
    public EmployeeBankDetailsResponseDto updateBankDetails(Long bankId, EmployeeBankDetailsRequestDto dto) {

        EmployeeBankDetails existing = bankRepo.findByEmployeeBankDetailsIdAndActiveTrue(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank details not found"));

        validationUtil.validateIFSC(dto.getIfsc());
        validationUtil.validateAccountNumber(dto.getAccountNumber());
        validationUtil.validateUAN(dto.getUanNumber());

        existing.setBankName(dto.getBankName());
        existing.setAccountHolderName(dto.getAccountHolderName());
        existing.setAccountNumber(dto.getAccountNumber());
        existing.setAccountType(dto.getAccountType());
        existing.setIfsc(dto.getIfsc());
        existing.setBranch(dto.getBranch());
        existing.setUanNumber(dto.getUanNumber());
        existing.setUpdatedAt(LocalDateTime.now());

        bankRepo.save(existing);

        log.info("Bank details updated for employeeId {}", existing.getEmployee());
        return convertToResponse(existing);
    }

    @Override
    public String deleteBankDetails(Long bankId) {
        EmployeeBankDetails existing = bankRepo.findByEmployeeBankDetailsIdAndActiveTrue(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        existing.setActive(false);
        existing.setUpdatedAt(LocalDateTime.now());
        bankRepo.save(existing);

        return "Bank details deleted successfully (Soft Delete)";
    }

    @Override
    public String hardDeleteBankDetails(Long bankId) {

        EmployeeBankDetails existing = bankRepo.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        bankRepo.delete(existing);

        return "Bank details deleted permanently (Hard Delete)";
    }



    private EmployeeBankDetailsResponseDto convertToResponse(EmployeeBankDetails entity) {
        return EmployeeBankDetailsResponseDto.builder()
                .employeeBankDetailsId(entity.getEmployeeBankDetailsId())
                .employeeId(entity.getEmployee().getEmployeeId())
                .employeeName(entity.getEmployee().getName())
                .employeeEmail(entity.getEmployee().getEmail())
                .department(entity.getEmployee().getDepartment())
                .accountNumber(MaskUtil.maskAccountNumber(entity.getAccountNumber()))
                .bankName(entity.getBankName())
                .accountHolderName(entity.getAccountHolderName())
                .accountType(entity.getAccountType())
                .ifsc(entity.getIfsc())
                .branch(entity.getBranch())
                .uanNumber(entity.getUanNumber())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}

