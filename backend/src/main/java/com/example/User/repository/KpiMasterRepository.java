package com.example.User.repository;

import com.example.User.entity.KpiMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KpiMasterRepository extends JpaRepository<KpiMaster, Long> {
    boolean existsByDepartment_DepartmentIdAndRoleAndKpiNameIgnoreCase(Long departmentId, Enum role,
                                                                       String kpiName);
}

