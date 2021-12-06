package com.web.controller;

import com.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    protected String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    protected String doPost(@RequestParam("userName") String username,
                            @RequestParam("password") String password,
                            Model model) {
        if (username.isEmpty()||password.isEmpty()){
            return "redirect:/registration";
        }
        boolean isSaved = registrationService.saveUser(username, password);
        if (isSaved) {
            return "redirect:/login";
        } else {
            return "redirect:/registration";
        }
    }
}

