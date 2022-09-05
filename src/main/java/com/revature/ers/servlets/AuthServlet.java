package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.LoginRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.dtos.responses.UserResponse;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.custom_exceptions.AuthenticationException;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthServlet(ObjectMapper mapper, TokenService tokenService, UserService userService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            LoginRequest request = mapper.readValue(req.getInputStream(), LoginRequest.class);
            UserResponse response = userService.login(request);
            Principal principal = new Principal(response.getId(), response.getUsername(), response.getRole());
            String token = tokenService.generateToken(principal);

            resp.setStatus(200);
            resp.setHeader("Authorization", token);
            resp.setContentType("application/json");

        } catch (InvalidRequestException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(404); // Bad Request
        } catch (AuthenticationException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(401); // Invalid Cred
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET\n");
    }
}