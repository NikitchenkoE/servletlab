package com.service.scheduleClean;

import com.db.jdbc.JdbcCookieDao;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Scheduler {
    private final JdbcCookieDao jdbcCookieDao;
    private final long cookieLifeTimeInMilliseconds;

    public Scheduler(JdbcCookieDao jdbcCookieDao, long cookieLifeTimeInMilliseconds) {
        this.jdbcCookieDao = jdbcCookieDao;
        this.cookieLifeTimeInMilliseconds = cookieLifeTimeInMilliseconds;
    }

    public void startScheduling() {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("jdbcCookieDao", jdbcCookieDao);
            jobDataMap.put("cookieLifeTimeInMilliseconds", cookieLifeTimeInMilliseconds);

            JobDetail jobDetail = JobBuilder.newJob(CookieCleaner.class)
                    .usingJobData(jobDataMap).build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("CookieCleanerTrigger")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(cookieLifeTimeInMilliseconds).repeatForever())
                    .build();

            org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
