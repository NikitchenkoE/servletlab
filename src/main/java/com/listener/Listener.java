package com.listener;

import com.ServiceLocator;
import com.db.DataSourceFactory;
import com.db.jdbc.JdbcCartDao;
import com.db.jdbc.JdbcProductDao;
import com.db.jdbc.JdbcSessionDao;
import com.db.jdbc.JdbcUserDao;
import com.service.CartService;
import com.service.ProductService;
import com.service.RegistrationService;
import com.service.SecurityService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = Listener.propertyLoader();
        DataSourceFactory dataSourceFactory = new DataSourceFactory(properties);
        DataSource dataSource = dataSourceFactory.getDataSource();

        JdbcCartDao jdbcCartDao = new JdbcCartDao(dataSource);
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSource);
        JdbcSessionDao jdbcSessionDao = new JdbcSessionDao(dataSource);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource);

        ServiceLocator.addDependency(ProductService.class, new ProductService(jdbcProductDao));
        ServiceLocator.addDependency(RegistrationService.class, new RegistrationService(jdbcUserDao));
        ServiceLocator.addDependency(SecurityService.class, new SecurityService(jdbcSessionDao, jdbcUserDao, Integer.parseInt(properties.getProperty("session.ExpirationDateInSeconds"))));
        ServiceLocator.addDependency(CartService.class, new CartService(jdbcCartDao, jdbcProductDao));
//
//        ProductService productService = new ProductService(jdbcProductDao);
//        RegistrationService registrationService = new RegistrationService(jdbcUserDao);
//        SecurityService securityService = new SecurityService(jdbcSessionDao, jdbcUserDao, Integer.parseInt(properties.getProperty("session.ExpirationDateInSeconds")));
//        CartService cartService = new CartService(jdbcCartDao, jdbcProductDao);
//
//        servletContextEvent.getServletContext().setAttribute("productService", productService);
//        servletContextEvent.getServletContext().setAttribute("registrationService", registrationService);
//        servletContextEvent.getServletContext().setAttribute("securityService", securityService);
//        servletContextEvent.getServletContext().setAttribute("cartService", cartService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private static Properties propertyLoader() {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException exception) {
            log.error("Problem when loading properties", exception);
            throw new RuntimeException("Problem when loading properties", exception);
        }
        return properties;
    }
}
