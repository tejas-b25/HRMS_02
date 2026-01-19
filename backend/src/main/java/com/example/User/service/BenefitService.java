package com.example.User.service;

import com.example.User.dto.BenefitMasterDTO;

import java.util.List;

public interface BenefitService {
    BenefitMasterDTO createBenefit(BenefitMasterDTO dto);
    BenefitMasterDTO getBenefitById(Long id);
    List<BenefitMasterDTO> getAllBenefits();
    BenefitMasterDTO updateBenefit(Long id, BenefitMasterDTO dto);
    void deleteBenefit(Long id);




}
