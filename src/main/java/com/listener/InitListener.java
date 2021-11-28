package com.listener;

import com.db.DataSourceFactory;
import com.filter.SecurityFilter;
import com.service.CartService;
import com.service.ProductService;
import com.service.RegistrationService;
import com.service.SecurityService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = InitListener.propertyLoader();
        DataSourceFactory dataSourceFactory = new DataSourceFactory(properties);
        DataSource dataSource = dataSourceFactory.getDataSource();

        ProductService productService = new ProductService(dataSource);
        RegistrationService registrationService = new RegistrationService(dataSource);
        SecurityService securityService = new SecurityService(dataSource,properties);
        CartService cartService = new CartService(dataSource);
        SecurityFilter securityFilter = new SecurityFilter();

        servletContextEvent.getServletContext().setAttribute("productService", productService);
        servletContextEvent.getServletContext().setAttribute("registrationService", registrationService);
        servletContextEvent.getServletContext().setAttribute("securityService", securityService);
        servletContextEvent.getServletContext().setAttribute("cartService", cartService);
        servletContextEvent.getServletContext().setAttribute("securityFilter", securityFilter);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private static Properties propertyLoader(){
        Properties properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/projectProp.properties")){
            properties.load(fileInputStream);
        } catch (IOException exception) {
            log.error("Problem when loading properties", exception);
            throw new RuntimeException("Problem when loading properties", exception);
        }
        return properties;
    }
}
