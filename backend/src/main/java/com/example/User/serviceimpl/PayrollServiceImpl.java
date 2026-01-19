package com.example.User.serviceimpl;

import com.example.User.dto.EmployeePayrollRequestDto;
import com.example.User.dto.EmployeePayrollResponseDto;
import com.example.User.dto.SalaryStructureRequestDto;
import com.example.User.dto.SalaryStructureResponseDto;
import com.example.User.entity.EmployeePayroll;
import com.example.User.entity.SalaryStructureMaster;
import com.example.User.repository.EmployeePayrollRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.SalaryStructureRepository;
import com.example.User.service.PayrollService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private SalaryStructureRepository structureRepo;

    @Autowired
    private EmployeePayrollRepository payrollRepo;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public SalaryStructureResponseDto createSalaryStructure(SalaryStructureRequestDto dto) {
        structureRepo.findByStructureName(dto.getStructureName())
                .ifPresent(s -> { throw new RuntimeException("Structure name already exists!"); });

        SalaryStructureMaster structure = new SalaryStructureMaster();
        structure.setStructureName(dto.getStructureName());
        structure.setBasicPay(dto.getBasicPay());
        structure.setHra(dto.getHra());
        structure.setVariablePay(dto.getVariablePay());
        structure.setOtherAllowances(dto.getOtherAllowances());
        structure.setDeductions(dto.getDeductions());
        structure.setCreatedBy("ADMIN");          // Added by Harshada
        structure.setIsActive(true);              // Added by Harshada

        structureRepo.save(structure);

        return mapToStructureResponse(structure);
    }

    @Override
    public SalaryStructureResponseDto updateSalaryStructure(Long id, SalaryStructureRequestDto dto) {
        SalaryStructureMaster structure = structureRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Structure not found"));
        structure.setStructureName(dto.getStructureName());  // Added by Harshada
        structure.setBasicPay(dto.getBasicPay());
        structure.setHra(dto.getHra());
        structure.setVariablePay(dto.getVariablePay());
        structure.setOtherAllowances(dto.getOtherAllowances());
        structure.setDeductions(dto.getDeductions());
        structure.setUpdatedBy("ADMIN");          // Added by Harshada
        structureRepo.save(structure);
        return mapToStructureResponse(structure);
    }

    @Override
    public EmployeePayrollResponseDto processPayroll(EmployeePayrollRequestDto dto) {
        payrollRepo.findByEmployeeIdAndMonth(dto.getEmployeeId(), dto.getMonth())
                .ifPresent(p -> { throw new RuntimeException("Payroll already processed for this month"); });

        SalaryStructureMaster structure = structureRepo.findById(dto.getStructureId())
                .orElseThrow(() -> new RuntimeException("Invalid Salary Structure"));
        boolean employeeExists = employeeRepository.existsById(dto.getEmployeeId());
        if (!employeeExists) {
            throw new ValidationException("Invalid Employee ID: " + dto.getEmployeeId());
        }

        double gross = structure.getBasicPay() + structure.getHra()
                + structure.getVariablePay() + structure.getOtherAllowances();
        double net = gross - structure.getDeductions();

        EmployeePayroll payroll = new EmployeePayroll();
        payroll.setEmployeeId(dto.getEmployeeId());
        payroll.setMonth(dto.getMonth());
        payroll.setSalaryStructure(structure);
        payroll.setGrossSalary(gross);
        payroll.setTotalDeductions(structure.getDeductions());
        payroll.setNetSalary(net);
        payroll.setPaymentStatus(EmployeePayroll.PaymentStatus.PENDING);
        payroll.setBankReferenceNo(dto.getBankReferenceNo());
        payroll.setCreatedBy("ADMIN");// Added by Harshada
        payroll.setUpdatedBy("ADMIN"); //Added by Harshada
        payroll.setIsActive(true);                // Added by Harshada
        payroll.setPaymentDate(LocalDate.now());   // Added by Harshada
        payrollRepo.save(payroll);

        return mapToPayrollResponse(payroll);
    }

    @Override
    public EmployeePayrollResponseDto getEmployeePayroll(Long employeeId, String month) {
        EmployeePayroll payroll = payrollRepo.findByEmployeeIdAndMonth(employeeId, month)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));
        return mapToPayrollResponse(payroll);
    }

    @Override
    public List<EmployeePayrollResponseDto> getAllPayrolls() {
        return payrollRepo.findAll().stream()
                .map(this::mapToPayrollResponse)
                .collect(Collectors.toList());
    }


    private SalaryStructureResponseDto mapToStructureResponse(SalaryStructureMaster s) {
        SalaryStructureResponseDto dto = new SalaryStructureResponseDto();
        dto.setStructureId(s.getStructureId());
        dto.setStructureName(s.getStructureName());
        dto.setBasicPay(s.getBasicPay());
        dto.setHra(s.getHra());
        dto.setVariablePay(s.getVariablePay());
        dto.setOtherAllowances(s.getOtherAllowances());
        dto.setDeductions(s.getDeductions());
        return dto;
    }

    private EmployeePayrollResponseDto mapToPayrollResponse(EmployeePayroll p) {
        EmployeePayrollResponseDto dto = new EmployeePayrollResponseDto();
        dto.setPayrollId(p.getPayrollId());
        dto.setEmployeeId(p.getEmployeeId());
        dto.setMonth(p.getMonth());
        dto.setStructureName(p.getSalaryStructure().getStructureName());
        dto.setGrossSalary(p.getGrossSalary());
        dto.setTotalDeductions(p.getTotalDeductions());
        dto.setNetSalary(p.getNetSalary());
        dto.setPaymentStatus(p.getPaymentStatus().name());
        dto.setBankReferenceNo(p.getBankReferenceNo());
        return dto;
    }
}
