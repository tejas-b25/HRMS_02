package com.example.User.repository;

import com.example.User.entity.Department;
import com.example.User.entity.DepartmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByNameIgnoreCase(String name);

    Optional<Department> findByCodeIgnoreCase(String code);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

    Page<Department> findAllByStatus(DepartmentStatus status, Pageable pageable);

    boolean existsByNameAndStatus(String name, DepartmentStatus status);


    //  Search by name or code (case-insensitive)
    @Query("SELECT d FROM Department d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(d.code) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Department> searchDepartments(@Param("query") String query);

    //  Filter by status
    List<Department> findByStatus(DepartmentStatus status);

    //  Search + Filter combined
    @Query("SELECT d FROM Department d WHERE " +
            "(LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(d.code) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND d.status = :status")
    List<Department> searchDepartmentsByStatus(@Param("query") String query, @Param("status") DepartmentStatus status);

}
