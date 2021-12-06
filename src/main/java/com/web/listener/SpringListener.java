package com.web.listener;

import com.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Component
public class SpringListener {
    private final ServletContext servletContext;
    private final SecurityService securityService;

    @Autowired
    public SpringListener(ServletContext servletContext, SecurityService securityService) {
        this.servletContext = servletContext;
        this.securityService = securityService;
    }

    @PostConstruct
    public void init() {
        servletContext.setAttribute("securityService", securityService);
    }

}
