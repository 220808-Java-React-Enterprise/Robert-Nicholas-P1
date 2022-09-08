package com.revature.ers.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.daos.*;
import com.revature.ers.services.ReimbursementService;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.servlets.AuthServlet;
import com.revature.ers.servlets.ManagerServlet;
import com.revature.ers.servlets.ReimbursementServlet;
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
        UserServlet userServlet = new UserServlet(mapper, new TokenService(new JwtConfig()), new UserService(new UserDAO(), new UserRoleDAO()));
        AuthServlet authServlet = new AuthServlet(mapper, new TokenService(new JwtConfig()), new UserService(new UserDAO(), new UserRoleDAO()));
        ReimbursementServlet reimbursementServlet = new ReimbursementServlet(mapper, new TokenService(new JwtConfig()), new UserService(new UserDAO(), new UserRoleDAO()),
                new ReimbursementService(new ReimbursementDAO(), new ReimbursementTypeDAO(), new ReimbursementStatusDAO()));
        ManagerServlet managerServlet = new ManagerServlet(mapper, new TokenService(new JwtConfig()), new UserService(new UserDAO(), new UserRoleDAO()),
                new ReimbursementService(new ReimbursementDAO(), new ReimbursementTypeDAO(), new ReimbursementStatusDAO()));


        // Need ServletContext class to map whatever servlet to url path
        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/login");
        context.addServlet("ReimbursementServlet", reimbursementServlet).addMapping("/reimb/*");
        context.addServlet("ManagerServlet", managerServlet).addMapping("/manager/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\nShutting down application");
    }
}
