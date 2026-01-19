package com.example.User.serviceimpl;

import com.example.User.dto.BenefitProviderDTO;
import com.example.User.entity.BenefitProvider;
import com.example.User.repository.BenefitProviderRepository;
import com.example.User.service.BenefitProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BenefitProviderServiceImpl implements BenefitProviderService {

    @Autowired
    private BenefitProviderRepository providerRepo;

    @Override
    public BenefitProviderDTO createProvider(BenefitProviderDTO dto) {

        BenefitProvider provider = BenefitProvider.builder()
                .providerName(dto.getProviderName())
                .contactPerson(dto.getContactPerson())
                .contactEmail(dto.getContactEmail())
                .contactPhone(dto.getContactPhone())
                .policyNumber(dto.getPolicyNumber())
                .renewalDate(dto.getRenewalDate())
                .address(dto.getAddress())
                .isActive(dto.getIsActive())
                .build();

        BenefitProvider saved = providerRepo.save(provider); // ✅ DB save

        return mapToDTO(saved); // ✅ VERY IMPORTANT
    }

    @Override
    public BenefitProviderDTO getProviderById(Long providerId) {
        BenefitProvider entity = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        return mapToDTO(entity);
    }

    @Override
    public List<BenefitProviderDTO> getAllProviders() {
        return providerRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BenefitProvider updateProvider(Long id, BenefitProvider entity) {

        BenefitProvider provider = providerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        provider.setProviderName(entity.getProviderName());
        provider.setContactPerson(entity.getContactPerson());
        provider.setContactEmail(entity.getContactEmail());
        provider.setContactPhone(entity.getContactPhone());
        provider.setPolicyNumber(entity.getPolicyNumber());
        provider.setRenewalDate(entity.getRenewalDate());
        provider.setAddress(entity.getAddress());
        provider.setIsActive(entity.getIsActive());

        return providerRepo.save(provider);
    }

    @Override
    public void deleteProvider(Long id) {
        providerRepo.deleteById(id);
    }

    // 🔁 ENTITY → DTO mapper
    private BenefitProviderDTO mapToDTO(BenefitProvider entity) {
        return BenefitProviderDTO.builder()
                .providerId(entity.getProviderId())
                .providerName(entity.getProviderName())
                .contactPerson(entity.getContactPerson())
                .contactEmail(entity.getContactEmail())
                .contactPhone(entity.getContactPhone())
                .policyNumber(entity.getPolicyNumber())
                .renewalDate(entity.getRenewalDate())
                .address(entity.getAddress())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}