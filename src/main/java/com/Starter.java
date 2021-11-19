package com;

import com.db.DataSourceFactory;
import com.servlet.ServletsHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;

@Slf4j
public class Starter {
    public static void main(String[] args) throws Exception {
        log.info("Server started");
        DataSourceFactory dataConnectionPull = new DataSourceFactory();
        ServletsHandler servletHandler = new ServletsHandler(dataConnectionPull);

        Server server = new Server(5000);
        server.setHandler(servletHandler.getServletContextHandler());
        server.start();
    }
}
