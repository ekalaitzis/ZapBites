package com.example.zapbites.Business;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BusinessService {

    private final BusinessRepository businessRepository;

    @Autowired
    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    public Optional<Business> getBusinessById(Long businessId) {
        return businessRepository.findById(businessId);
    }

    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }

    public Business updateBusiness(Long businessId, Business updatedBusiness) {
        Optional<Business> existingBusiness = businessRepository.findById(businessId);

        if (existingBusiness.isPresent()) {
            Business businessToUpdate = existingBusiness.get();
            businessToUpdate.setCompanyName(updatedBusiness.getCompanyName());
            businessToUpdate.setEmail(updatedBusiness.getEmail());
            businessToUpdate.setPassword(updatedBusiness.getPassword());
            businessToUpdate.setTelephone(updatedBusiness.getTelephone());
            businessToUpdate.setTaxIdNumber(updatedBusiness.getTaxIdNumber());
            return businessRepository.save(businessToUpdate);
        } else {
            return null;
        }
    }

    public boolean deleteBusinessById(Long id) {
        Optional<Business> toBeDeleted = businessRepository.findById(id);
        if (toBeDeleted.isPresent()) {
            businessRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
