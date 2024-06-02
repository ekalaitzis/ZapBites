package com.example.zapbites.Business;

import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;
    private final BCryptPasswordEncoder encoder;


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
        business.setPassword(encoder.encode(business.getPassword()));
        return businessRepository.save(business);
    }

    @Override
    public Business updateBusiness(Business updatedBusiness) {
        updatedBusiness.setPassword(encoder.encode(updatedBusiness.getPassword()));
        return businessRepository.save(updatedBusiness);
    }

    @Override
    public void deleteBusinessById(Long id) {
        businessRepository.deleteById(id);
    }
}
