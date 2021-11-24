package com;

import com.db.DataSourceFactory;
import com.service.LoginService;
import com.service.ProductService;
import com.service.RegistrationService;
import com.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@Slf4j
public class Starter {
    public static void main(String[] args) throws Exception {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        ProductService productService = new ProductService(dataSourceFactory);
        RegistrationService registrationService = new RegistrationService(dataSourceFactory);
        LoginService loginService = new LoginService(dataSourceFactory);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new MainPageServlet(productService, loginService)), "/products");
        servletContextHandler.addServlet(new ServletHolder(new MainPageServlet(productService, loginService)), "/");
        servletContextHandler.addServlet(new ServletHolder(new AddProductPageServlet(productService, loginService)), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(new UpdateProductServlet(productService, loginService)), "/products/update");
        servletContextHandler.addServlet(new ServletHolder(new DeleteServlet(productService, loginService)), "/products/delete");
        servletContextHandler.addServlet(new ServletHolder(new RegistrationServlet(registrationService, loginService)), "/registration");
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(loginService)), "/login");
        servletContextHandler.addServlet(new ServletHolder(new LogoutServlet(loginService)), "/logout");

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
