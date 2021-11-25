package com.service.scheduleClean;

import com.db.JdbcCookieDao;
import com.entity.CookieEntity;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Date;

public class CookieCleaner implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        JdbcCookieDao jdbcCookieDao = (JdbcCookieDao) jobDataMap.get("jdbcCookieDao");
        long cookieLifeTimeInMilliseconds = jobDataMap.getLong("cookieLifeTimeInMilliseconds");

        long presentTime = new Date().getTime();
        var cookies = jdbcCookieDao.getAllCookies();
        if (!cookies.isEmpty()) {
            for (CookieEntity cookie : cookies) {
                if ((cookie.getExpireDate() + cookieLifeTimeInMilliseconds) >= presentTime) {
                    jdbcCookieDao.delete(cookie.getCookie());
                }
            }
        }
    }
}
