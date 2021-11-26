package com.service.scheduleClean;

import com.db.JdbcCookieDao;
import com.entity.CookieEntity;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Date;

public class CookieCleaner implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        JdbcCookieDao jdbcCookieDao = (JdbcCookieDao) jobDataMap.get("jdbcCookieDao");
        long cookieLifeTimeInMilliseconds = jobDataMap.getLong("cookieLifeTimeInMilliseconds");
        var cookies = jdbcCookieDao.getAllCookies();
        if (!cookies.isEmpty()) {

            for (CookieEntity cookie : cookies) {
                if ((cookie.getExpireDate() + cookieLifeTimeInMilliseconds) <= new Date().getTime()) {
                    jdbcCookieDao.delete(cookie.getCookie());
                }
            }
        }
    }
}
