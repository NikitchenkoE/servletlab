package com.service.scheduleClean;

import com.db.jdbc.JdbcSessionDao;
import com.entity.Session;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Date;

public class CookieCleaner implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        JdbcSessionDao jdbcCookieDao = (JdbcSessionDao) jobDataMap.get("jdbcCookieDao");
        long cookieLifeTimeInMilliseconds = jobDataMap.getLong("cookieLifeTimeInMilliseconds");
        var cookies = jdbcCookieDao.getAllCookies();
        if (!cookies.isEmpty()) {

            for (Session cookie : cookies) {
                if ((cookie.getExpireDate() + cookieLifeTimeInMilliseconds) <= new Date().getTime()) {
                    jdbcCookieDao.delete(cookie.getToken());
                }
            }
        }
    }
}
