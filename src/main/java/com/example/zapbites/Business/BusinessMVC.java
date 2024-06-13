package com.example.zapbites.Business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/businesses")
@Validated
public class BusinessMVC {


    private final BusinessService businessService;

    @Autowired
    public BusinessMVC(BusinessService businessService) {
        this.businessService = businessService;
    }


    @GetMapping("/index")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "business_Login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("business", new Business());
        return "business_register";
    }

    @GetMapping("/test")
    public String showTestTemplate(Model model) {
        return "test";
    }


    @PostMapping("/register/save")
    public String register(@ModelAttribute("business") Business business) {
        businessService.createBusiness(business);
        return "redirect:/businesses/index";
    }

}
