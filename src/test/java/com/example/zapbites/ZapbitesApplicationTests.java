package com.example.zapbites;

import com.example.zapbites.Business.BusinessRepository;
import com.example.zapbites.Business.BusinessService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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

}
