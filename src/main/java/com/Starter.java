package com;

import com.db.DataSourceFactory;
import com.servlet.ServletsHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;

@Slf4j
public class Starter {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        var portString = System.getenv("PORT");
        DataSourceFactory dataConnectionPull = new DataSourceFactory();
        ServletsHandler servletHandler = new ServletsHandler(dataConnectionPull);

        try {
            port = Integer.parseInt(portString);
        }catch (NumberFormatException e){
            log.error("Can't see port from env");
        }
        Server server = new Server(port);
        server.setHandler(servletHandler.getServletContextHandler());
        server.start();
        server.join();
        log.info("Server started");
    }
}
