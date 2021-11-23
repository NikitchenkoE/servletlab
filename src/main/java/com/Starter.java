package com;

import com.db.DataSourceFactory;
import com.service.ProductService;
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

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new MainPageServlet(productService)), "/products");
        servletContextHandler.addServlet(new ServletHolder(new MainPageServlet(productService)), "/");
        servletContextHandler.addServlet(new ServletHolder(new AddProductPageServlet(productService)), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(new UpdateProductServlet(productService)), "/products/update");
        servletContextHandler.addServlet(new ServletHolder(new UpdateProductServlet(productService)), "/products/delete");

        int port = 8080;
        var portString = System.getenv("PORT");
        try {
            port = Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            log.error("Can't see port from env. Will use {}",port);
        }

        Server server = new Server(port);
        server.setHandler(servletContextHandler);
        server.start();
        server.join();
        log.info("Server started");
    }
}
