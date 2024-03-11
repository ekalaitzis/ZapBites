package com.example.zapbites.Business;

import com.example.zapbites.Business.Exceptions.BusinessNotFoundException;
import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String email = business.getEmail();
        Optional<Business> existingBusiness = businessRepository.findByEmail(email);

        if (existingBusiness.isPresent()) {
            throw new DuplicateBusinessException("Business with email " + email + " already exists");
        }
        return businessRepository.save(business);
    }


    public Business updateBusiness(Business updatedBusiness) {
        List<Business> allBusinesses = getAllBusinesses();

        if (allBusinesses.stream().anyMatch(b -> b.getId().equals(updatedBusiness.getId()))) {
            return businessRepository.save(updatedBusiness);
        } else {
            throw new BusinessNotFoundException("Business with id " + updatedBusiness.getCompanyName() + " not found.");
        }
    }

    public void deleteBusinessById(Long id) {
        try {
            businessRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessNotFoundException("Business with id " + id + " not found", e);
        }
    }
}
