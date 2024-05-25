package com.example.zapbites.Search;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.BusinessRepository;
import com.example.zapbites.Product.Product;
import com.example.zapbites.Product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SearchService {

    private final BusinessRepository businessRepository;
    private final ProductRepository productRepository;

public Map<String, List<?>> search(String query) {
    List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
    List<Business> businesses = businessRepository.findByCompanyNameContainingIgnoreCase(query);

    Map<String, List<?>> results = new HashMap<>();
    results.put("products", products);
    results.put("businesses", businesses);

    return results;}
}
