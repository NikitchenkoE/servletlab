package com.servlets;

import com.db.DataSourceFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServletsHandler {
    private final DataSourceFactory dataSourceFactory;

    public ServletsHandler(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    public ServletContextHandler getServletContextHandler() {
        MainPageServlet mainPageServlet = new MainPageServlet(dataSourceFactory.getDataSource());
        AddProductPageServlet addProductPageServlet = new AddProductPageServlet(dataSourceFactory.getDataSource());
        UpdateProductServlet updateProductServlet = new UpdateProductServlet(dataSourceFactory.getDataSource());


        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(mainPageServlet), "/products");
        servletContextHandler.addServlet(new ServletHolder(addProductPageServlet), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(updateProductServlet), "/products/update");

        return servletContextHandler;
    }
}