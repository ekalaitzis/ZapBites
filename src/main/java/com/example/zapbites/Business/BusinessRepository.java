package com.example.zapbites.Business;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByEmail(String email);
    Optional<Business> findByCompanyName(String companyName);
    Optional<Business> findByTelephone(String telephone);
    List<Business> findByCompanyNameContainingIgnoreCase(String companyName);



}
