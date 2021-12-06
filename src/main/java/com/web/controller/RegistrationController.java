package com.web.controller;

import com.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    private final SecurityService securityService;

    public RegistrationController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/registration")
    protected String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    protected String doPost(@RequestParam("userName") String username,
                            @RequestParam("password") String password) {
        if (username.isEmpty()||password.isEmpty()){
            return "redirect:/registration";
        }
        securityService.saveUser(username, password);
        return "redirect:/login";
    }
}

