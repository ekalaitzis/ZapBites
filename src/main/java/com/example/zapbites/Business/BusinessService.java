package com.example.zapbites.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessService {


    /**
     * Retrieves all businesses
     *
     * @return
     */
    List<Business> getAllBusinesses();

    /**
     * @param businessId The id of the business
     * @return The business with the requested id
     */

    Optional<Business> getBusinessById(Long businessId);

    /**
     * @param createdBusiness The business object to be created
     * @return The business object that was created
     */

    Business createBusiness(Business createdBusiness);

    /**
     * @param updatedBusiness The business object to be updated
     * @return The updated business object
     */


    Business updateBusiness(Business updatedBusiness);

    /**
     * @param id the id of the business to be deleted
     */

    void deleteBusinessById(Long id);


}
