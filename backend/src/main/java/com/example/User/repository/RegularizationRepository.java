package com.example.User.repository;

import com.example.User.entity.AttendanceRegularization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularizationRepository extends JpaRepository<AttendanceRegularization, Long> {
}
