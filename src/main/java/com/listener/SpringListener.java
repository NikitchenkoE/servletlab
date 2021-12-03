package com.listener;

import com.service.scheduleClean.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpringListener {
    private final Scheduler scheduler;

    @Autowired
    public SpringListener(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        scheduler.startScheduling();
    }

}
