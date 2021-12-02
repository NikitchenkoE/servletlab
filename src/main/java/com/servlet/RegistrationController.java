package com.servlet;

import com.service.RegistrationService;
import com.service.util.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    @ResponseBody
    protected byte[] getRegistrationPage(){
        return PageGenerator.init().writePage("registration.ftl");
    }

    @PostMapping("/registration")
    protected String doPost(@RequestParam("userName") String username, @RequestParam("password") String password) {
            boolean isSaved = registrationService.saveUser(username, password);
            if (isSaved) {
                return "redirect:/login";
            } else {
                return "redirect:/registration";
            }
        }
    }

