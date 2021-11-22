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
        int port = 8080;
        var portString = System.getenv("PORT");
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        ProductService productService = new ProductService(dataSourceFactory);

        MainPageServlet mainPageServlet = new MainPageServlet(productService);
        AddProductPageServlet addProductPageServlet = new AddProductPageServlet(productService);
        UpdateProductServlet updateProductServlet = new UpdateProductServlet(productService);
        DeleteServlet deleteServlet = new DeleteServlet(productService);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(mainPageServlet), "/products");
        servletContextHandler.addServlet(new ServletHolder(mainPageServlet), "/");
        servletContextHandler.addServlet(new ServletHolder(addProductPageServlet), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(updateProductServlet), "/products/update");
        servletContextHandler.addServlet(new ServletHolder(deleteServlet), "/products/delete");

        try {
            port = Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            log.error("Can't see port from env");
        }

        Server server = new Server(port);
        server.setHandler(servletContextHandler);
        server.start();
        server.join();
        log.info("Server started");
    }
}
