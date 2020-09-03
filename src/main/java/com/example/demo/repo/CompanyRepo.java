package com.example.demo.repo;

import com.example.demo.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findCompanyEntityByCompanySymbol(String companySymbol);
}
