package com.example.User.serviceimpl;
import com.example.User.dto.BenefitMasterDTO;
import com.example.User.entity.BenefitMaster;
import com.example.User.repository.BenefitMasterRepository;
import com.example.User.service.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BenefitServiceImpl implements BenefitService {

    @Autowired
    private BenefitMasterRepository benefitRepo;

    @Override
    public BenefitMasterDTO createBenefit(BenefitMasterDTO dto) {
        if (benefitRepo.existsByBenefitName(dto.getBenefitName())) {
            throw new RuntimeException("Benefit with this name already exists.");
        }

        BenefitMaster entity = new BenefitMaster();
        entity.setBenefitName(dto.getBenefitName());
        entity.setBenefitType(dto.getBenefitType());
        entity.setDescription(dto.getDescription());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true); // added by Harshada
        entity.setCreatedBy("ADMIN");  // added by Harshada
        entity.setUpdatedBy("ADMIN");  // added by Harshada

        // Save entity to get generated ID and timestamps
        benefitRepo.save(entity);

        // Populate DTO fully from entity to avoid nulls
        dto.setBenefitId(entity.getBenefitId()); // added by Harshada
        dto.setCreatedAt(entity.getCreatedAt()); // added by Harshada
        dto.setUpdatedAt(entity.getUpdatedAt()); // added by Harshada
        dto.setCreatedBy(entity.getCreatedBy()); // added by Harshada
        dto.setUpdatedBy(entity.getUpdatedBy()); // added by Harshada

        return dto;
    }

    @Override
    public BenefitMasterDTO getBenefitById(Long id) {
        BenefitMaster entity = benefitRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefit not found with ID: " + id));

        BenefitMasterDTO dto = new BenefitMasterDTO();
        dto.setBenefitId(entity.getBenefitId()); // added by Harshada
        dto.setBenefitName(entity.getBenefitName());
        dto.setBenefitType(entity.getBenefitType());
        dto.setDescription(entity.getDescription());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt()); // added by Harshada
        dto.setUpdatedAt(entity.getUpdatedAt()); // added by Harshada
        dto.setCreatedBy(entity.getCreatedBy()); // added by Harshada
        dto.setUpdatedBy(entity.getUpdatedBy()); // added by Harshada

        return dto;
    }

    @Override
    public List<BenefitMasterDTO> getAllBenefits() {
        return benefitRepo.findAll().stream().map(b -> {
            BenefitMasterDTO dto = new BenefitMasterDTO();
            dto.setBenefitId(b.getBenefitId()); // added by Harshada
            dto.setBenefitName(b.getBenefitName());
            dto.setBenefitType(b.getBenefitType());
            dto.setDescription(b.getDescription());
            dto.setIsActive(b.getIsActive());
            dto.setCreatedAt(b.getCreatedAt()); // added by Harshada
            dto.setUpdatedAt(b.getUpdatedAt()); // added by Harshada
            dto.setCreatedBy(b.getCreatedBy()); // added by Harshada
            dto.setUpdatedBy(b.getUpdatedBy()); // added by Harshada
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public BenefitMasterDTO updateBenefit(Long id, BenefitMasterDTO dto) {
        BenefitMaster entity = benefitRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefit not found"));

        entity.setBenefitName(dto.getBenefitName());
        entity.setBenefitType(dto.getBenefitType());
        entity.setDescription(dto.getDescription());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true); // added by Harshada
        entity.setUpdatedBy("ADMIN"); // added by Harshada

        benefitRepo.save(entity);

        // Populate DTO fully to avoid nulls
        dto.setBenefitId(entity.getBenefitId()); // added by Harshada
        dto.setCreatedAt(entity.getCreatedAt()); // added by Harshada
        dto.setUpdatedAt(entity.getUpdatedAt()); // added by Harshada
        dto.setCreatedBy(entity.getCreatedBy()); // added by Harshada
        dto.setUpdatedBy(entity.getUpdatedBy()); // added by Harshada

        return dto;
    }

    @Override
    public void deleteBenefit(Long id) {
        benefitRepo.deleteById(id);
    }
}
