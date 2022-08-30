package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.UserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;
import com.revature.ers.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final UserService userService;

    public UserServlet(ObjectMapper mapper, TokenService tokenService, UserService userService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserRequest request = mapper.readValue(req.getInputStream(), UserRequest.class);

            String[] path = req.getRequestURI().split("/");

            if (path[3].equalsIgnoreCase("signup")){
                User createdUser = userService.register(request, "DEFAULT");

                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(createdUser.getId()));
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try {
            if (principal.getRoleId().equals(userService.getRoleIdByUserId(principal.getId()))) {
                String username = req.getParameter("username");

                resp.setStatus(200);
                resp.setContentType("application/json");

            }

        } catch (NullPointerException e){
            resp.setStatus(401); // Unauthorized
        } catch (InvalidRequestException e){
            resp.setStatus(404); // Bad Request
        }
    }
}