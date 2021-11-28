package com;


public class Starter {
//    public static void main(String[] args) throws Exception {
//        Properties properties = Starter.propertyLoader();
//        DataSourceFactory dataSourceFactory = new DataSourceFactory(properties);
//        ProductService productService = new ProductService(dataSourceFactory);
//        RegistrationService registrationService = new RegistrationService(dataSourceFactory);
//        SecurityService securityService = new SecurityService(dataSourceFactory,properties);
//        CartService cartService = new CartService(dataSourceFactory.getDataSource());
//        SecurityFilter securityFilter = new SecurityFilter(securityService);
//
//
//        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        servletContextHandler.addServlet(new ServletHolder(new MainPageServlet(productService, securityService)), "/products");
//        servletContextHandler.addServlet(new ServletHolder(new MainPageServlet(productService, securityService)), "/");
//        servletContextHandler.addServlet(new ServletHolder(new AddProductPageServlet(productService, securityService)), "/products/add");
//        servletContextHandler.addServlet(new ServletHolder(new UpdateProductServlet(productService, securityService)), "/products/update");
//        servletContextHandler.addServlet(new ServletHolder(new DeleteServlet(productService, securityService, cartService)), "/products/delete");
//        servletContextHandler.addServlet(new ServletHolder(new RegistrationServlet(registrationService, securityService)), "/registration");
//        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");
//        servletContextHandler.addServlet(new ServletHolder(new LogoutServlet(securityService)), "/logout");
//        servletContextHandler.addServlet(new ServletHolder(new CartServlet(cartService,securityService)), "/cart");
//        servletContextHandler.addServlet(new ServletHolder(new DeleteFromCartServlet(cartService)),"/cart/deleteFromCart");
//
//        servletContextHandler.addFilter(new FilterHolder(securityFilter),"/*", EnumSet.allOf(DispatcherType.class));
//
//        int port = 8080;
//        var portString = System.getenv("PORT");
//        try {
//            port = Integer.parseInt(portString);
//        } catch (NumberFormatException e) {
//            log.error("Can't see port from env. Will use {}", port);
//        }
//
//        Server server = new Server(port);
//        server.setHandler(servletContextHandler);
//        server.start();
//        server.join();
//        log.info("Server started");
//    }
//
//    private static Properties propertyLoader(){
//        Properties properties = new Properties();
//        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/projectProp.properties")){
//            properties.load(fileInputStream);
//        } catch (IOException exception) {
//            log.error("Problem when loading properties", exception);
//            throw new RuntimeException("Problem when loading properties", exception);
//        }
//        return properties;
//    }
}
