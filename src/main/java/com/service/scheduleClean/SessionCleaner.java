package com.service.scheduleClean;

import com.db.jdbc.JdbcSessionDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Date;

@Slf4j
public class SessionCleaner implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("Execute session cleaner");
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        JdbcSessionDao jdbcSessionDao = (JdbcSessionDao) jobDataMap.get("jdbcCookieDao");
        long cookieLifeTimeInMilliseconds = jobDataMap.getLong("cookieLifeTimeInMilliseconds");

        jdbcSessionDao.cleanExpiredCookie(new Date().getTime() - cookieLifeTimeInMilliseconds);
    }
}

