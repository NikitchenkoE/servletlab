package com;

import com.db.DataSourceFactory;
import com.filter.SecurityFilter;
import com.service.CartService;
import com.service.SecurityService;
import com.service.ProductService;
import com.service.RegistrationService;
import com.servlet.*;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;

@Slf4j
public class Starter {
    public static void main(String[] args) throws Exception {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        ProductService productService = new ProductService(dataSourceFactory);
        RegistrationService registrationService = new RegistrationService(dataSourceFactory);
        SecurityService securityService = new SecurityService(dataSourceFactory);
        CartService cartService = new CartService(dataSourceFactory.getDataSource());
        SecurityFilter securityFilter = new SecurityFilter(securityService);


        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new MainPageServlet(productService, securityService)), "/products");
        servletContextHandler.addServlet(new ServletHolder(new MainPageServlet(productService, securityService)), "/");
        servletContextHandler.addServlet(new ServletHolder(new AddProductPageServlet(productService, securityService)), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(new UpdateProductServlet(productService, securityService)), "/products/update");
        servletContextHandler.addServlet(new ServletHolder(new DeleteServlet(productService, securityService, cartService)), "/products/delete");
        servletContextHandler.addServlet(new ServletHolder(new RegistrationServlet(registrationService, securityService)), "/registration");
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");
        servletContextHandler.addServlet(new ServletHolder(new LogoutServlet(securityService)), "/logout");
        servletContextHandler.addServlet(new ServletHolder(new CartServlet(cartService,securityService)), "/cart");
        servletContextHandler.addServlet(new ServletHolder(new DeleteFromCartServlet(cartService)),"/cart/deleteFromCart");

        servletContextHandler.addFilter(new FilterHolder(securityFilter),"/*", EnumSet.allOf(DispatcherType.class));

        int port = 8080;
        var portString = System.getenv("PORT");
        try {
            port = Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            log.error("Can't see port from env. Will use {}", port);
        }

        Server server = new Server(port);
        server.setHandler(servletContextHandler);
        server.start();
        server.join();
        log.info("Server started");
    }
}
