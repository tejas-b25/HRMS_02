package com.example.User.repository;

import com.example.User.entity.ShiftMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShiftMasterRepository extends JpaRepository<ShiftMaster, Long> {
    Optional<ShiftMaster> findByShiftName(String shiftName);
    Optional<ShiftMaster> findByShiftNameAndShiftIdNot(String shiftName, Long shiftId);
}
