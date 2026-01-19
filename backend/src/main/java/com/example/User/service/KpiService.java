package com.example.User.service;

import com.example.User.dto.KpiMasterDto;

import java.util.List;

public interface KpiService {
    KpiMasterDto createKpi(KpiMasterDto dto);
    KpiMasterDto updateKpi(Long id, KpiMasterDto dto);
    KpiMasterDto getKpiById(Long id);
    List<KpiMasterDto> getAllKpi();
    void deleteKpi(Long id);
}
