package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.models.User;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.custom_exceptions.custom_exceptions.InvalidRequestException;
import com.revature.ers.utils.custom_exceptions.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserService userService;

    public UserServlet(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);

            String[] path = req.getRequestURI().split("/");

            if (path[3].equalsIgnoreCase("signup")){
                User createdUser = userService.register(request);

                resp.getWriter().write(mapper.writeValueAsString(request));
//                resp.setStatus(200);
//                resp.setContentType("application/json");
//                resp.getWriter().write(mapper.writeValueAsString(createdUser.getId()));
            }
            else if (path[3].equalsIgnoreCase("login")){

            }
            else;

        } catch (InvalidRequestException e){
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException e){
            resp.setStatus(409);    // Conflict
        }
    }
}
