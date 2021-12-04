package com.listener;

import com.service.SecurityService;
import com.service.scheduleClean.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Component
public class SpringListener {
    private final Scheduler scheduler;
    private final ServletContext servletContext;
    private final SecurityService securityService;

    @Autowired
    public SpringListener(Scheduler scheduler, ServletContext servletContext, SecurityService securityService) {
        this.scheduler = scheduler;
        this.servletContext = servletContext;
        this.securityService = securityService;
    }

    @PostConstruct
    public void init() {
        scheduler.startScheduling();
        servletContext.setAttribute("securityService", securityService);
    }

}
