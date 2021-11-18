package com;

import com.db.DataConnectionPull;
import com.db.TableCreator;
import com.servlets.AddProductPageServlet;
import com.servlets.MainPageServlet;
import com.servlets.UpdateProductServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@Slf4j
public class Starter {
    public static void main(String[] args) throws Exception {
        log.info("Server started");
        DataConnectionPull dataConnectionPull = new DataConnectionPull();
        TableCreator tableCreator = new TableCreator(dataConnectionPull.getDataSource());
        tableCreator.createTableProducts();

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
