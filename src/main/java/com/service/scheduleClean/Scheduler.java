package com.service.scheduleClean;

import com.db.interfaces.SessionDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@Slf4j
public class Scheduler {
    private final SessionDao sessionDao;
    private final long sessionLifeTimeInMilliseconds;

    public Scheduler(SessionDao sessionDao, long cookieLifeTimeInMilliseconds) {
        this.sessionDao = sessionDao;
        this.sessionLifeTimeInMilliseconds = cookieLifeTimeInMilliseconds;
    }

    public void startScheduling() {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("jdbcCookieDao", sessionDao);
            jobDataMap.put("cookieLifeTimeInMilliseconds", sessionLifeTimeInMilliseconds);

            JobDetail jobDetail = JobBuilder.newJob(SessionCleaner.class)
                    .usingJobData(jobDataMap).build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("CookieCleanerTrigger")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(sessionLifeTimeInMilliseconds).repeatForever())
                    .build();

            org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException exception) {
            log.error("Exception when starting scheduling", exception);
            throw new RuntimeException("Exception when starting scheduling", exception);
        }
    }
}
