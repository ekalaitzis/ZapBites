package com.example.zapbites;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.BusinessRepository;
import com.example.zapbites.Business.BusinessService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
class ZapbitesApplicationTests {
    @MockBean
    private BusinessRepository businessRepository;
    @Autowired
    private BusinessService businessService;


    @Test
    void contextLoads() {
    }



//    @Test
//    public void moreBusinessAreRetrieves() {
//        List<Business> input = Arrays.asList();
//        given(businessRepository.findAll()).willReturn(Collections.emptyList());
//        List<Business> result = businessService.getAllBusinesses();
//
//        assertTrue(result.isEmpty());
//    }
//    public void noBusinessReturnedasddasdas() {
//        given(businessRepository.findAll()).willReturn(Collections.emptyList());
//        List<Business> result = businessService.getAllBusinesses();
//
//        assertTrue(result.isEmpty());
//    }

}
