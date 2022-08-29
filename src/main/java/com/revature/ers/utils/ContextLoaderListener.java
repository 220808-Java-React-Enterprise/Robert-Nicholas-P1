package com.revature.ers.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.daos.UserDAO;
import com.revature.ers.services.UserService;
import com.revature.ers.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // ObjectMapper provides functionality for reading adn writing JSON, either to and from basic POJOs (Plain Old Java Objects)
        ObjectMapper mapper = new ObjectMapper();

        // Dependency Injection
        UserServlet userServlet = new UserServlet(mapper, new UserService(new UserDAO()));

        // Need ServletContext class to map whatever servlet to url path
        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/user/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\nShutting down application");
    }
}
