package com.service.scheduleClean;

import com.db.JdbcCookieDao;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CookieScheduler {
    private final JdbcCookieDao jdbcCookieDao;
    private long cookieLifeTimeInMilliseconds;

    public CookieScheduler(JdbcCookieDao jdbcCookieDao, long cookieLifeTimeInSeconds) {
        this.jdbcCookieDao = jdbcCookieDao;
        this.cookieLifeTimeInMilliseconds = cookieLifeTimeInSeconds;
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

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
