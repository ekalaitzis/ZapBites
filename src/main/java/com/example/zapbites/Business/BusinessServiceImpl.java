package com.example.zapbites.Business;

import com.example.zapbites.Business.Exceptions.BusinessNotFoundException;
import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;

    @Autowired
    public BusinessServiceImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    @Override
    public Optional<Business> getBusinessById(Long businessId) {
        return businessRepository.findById(businessId);
    }

    @Override
    public Business createBusiness(Business business) {
        String email = business.getEmail();
        Optional<Business> existingBusiness = businessRepository.findByEmail(email);

        if (existingBusiness.isPresent()) {
            throw new DuplicateBusinessException("Business with email " + email + " already exists");
        }
        return businessRepository.save(business);
    }

    @Override
    public Business updateBusiness(Business updatedBusiness) {
        List<Business> allBusinesses = getAllBusinesses();

        if (allBusinesses.stream().anyMatch(b -> b.getId().equals(updatedBusiness.getId()))) {
            return businessRepository.save(updatedBusiness);
        } else {
            throw new BusinessNotFoundException("Business: " + updatedBusiness.getCompanyName() + " not found.");
        }
    }

    @Override
    public void deleteBusinessById(Long id) {
        businessRepository.deleteById(id);
    }
}
