package com.example.zapbites.Business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        return businessRepository.save(business);
    }

    public Business updateBusiness(Business updatedBusiness) {
       try {
           return businessRepository.save(updatedBusiness);
       } catch (DataAccessException e) {
           throw new EntityNotFoundException("Business with id " + updatedBusiness.getId() + " not found.", e);
       }
    }

    public void deleteBusinessById(Long id) {
    try {
        businessRepository.deleteById(id);
    } catch(EmptyResultDataAccessException e) {
        throw new EntityNotFoundException("Business with id " + id + " not found", e);
        }
    }
}
