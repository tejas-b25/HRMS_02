package com.example.User.repository;



import com.example.User.entity.BenefitProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitProviderRepository extends JpaRepository<BenefitProvider, Long> {
}

