package com.example.User.service;

import com.example.User.dto.BenefitProviderDTO;
import com.example.User.entity.BenefitProvider;

import java.util.List;

public interface BenefitProviderService {
    BenefitProviderDTO createProvider(BenefitProviderDTO dto);
    BenefitProviderDTO getProviderById(Long providerId);
    List<BenefitProviderDTO> getAllProviders();
    BenefitProvider updateProvider(Long id, BenefitProvider entity);
    void deleteProvider(Long id);
}
