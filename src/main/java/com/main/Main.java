package com.main;

import com.db.DataConnectionPull;
import com.servlets.AddProductPageServlet;
import com.servlets.MainPageServlet;
import com.servlets.UpdateProductServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        DataConnectionPull dataConnectionPull = new DataConnectionPull();

        MainPageServlet mainPageServlet = new MainPageServlet(dataConnectionPull.getDataSource());
        AddProductPageServlet addProductPageServlet = new AddProductPageServlet(dataConnectionPull.getDataSource());
        UpdateProductServlet updateProductServlet = new UpdateProductServlet(dataConnectionPull.getDataSource());


        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(mainPageServlet), "/products");
        servletContextHandler.addServlet(new ServletHolder(addProductPageServlet), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(updateProductServlet), "/products/update");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.start();
    }
}
