package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.UserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.dtos.responses.UserResponse;
import com.revature.ers.models.User;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.custom_exceptions.AuthenticationException;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;
import com.revature.ers.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try {
            UserRequest request = mapper.readValue(req.getInputStream(), UserRequest.class);

            String[] path = req.getRequestURI().split("/");

            if (path[3].equalsIgnoreCase("signup")){
                userService.register(request);

                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString("Sign up request submitted."));
            }
            else throw new InvalidRequestException("Not a valid path");

        } catch (NullPointerException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(401);    // Unauthorized
        } catch (InvalidRequestException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException e){
            resp.getWriter().write(mapper.writeValueAsString(e.toString()));
            resp.setStatus(409);    // Conflict
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try {
            // If role is Admin
            if (principal.getRole().equals("ADMIN")){
                Map<String, UserResponse> ls = userService.getUserList();
                String id;
                // If id is not null print detailed reimbursement
                if ((id = req.getParameter("id")) != null)
                    resp.getWriter().write(ls.get(id).toStringAdmin());
                    // Print list
                else{
                    resp.getWriter().write("<ul>");
                    for (UserResponse r : ls.values()){
                        resp.getWriter().write("<li>" + r.toStringAdmin() + "</li>");
                    }
                    resp.getWriter().write("</ul>");
                }

                resp.setStatus(200);
                resp.setContentType("application/json");
            }
            else resp.setStatus(401); // Unauthorized

        } catch (NullPointerException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(401); // Unauthorized
        } catch (InvalidRequestException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(404); // Bad Request
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try {
            UserRequest request = mapper.readValue(req.getInputStream(), UserRequest.class);

            String[] path = req.getRequestURI().split("/");

            if (path[3].equalsIgnoreCase("admin")){
                if (!principal.getRole().equals("ADMIN")){
                    throw new AuthenticationException("Not an authenticated user");
                }
                User user = userService.getUserById(req.getParameter("id"));
                userService.updateUser(user, request);
            }
            else throw new InvalidRequestException("Not a valid path");

        } catch (NullPointerException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(401);    // Unauthorized
        } catch (InvalidRequestException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(409);    // Conflict
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try {
            String[] path = req.getRequestURI().split("/");

            if (path[3].equalsIgnoreCase("admin")){
                if (!principal.getRole().equals("ADMIN")){
                    throw new AuthenticationException("Not an authenticated user");
                }
                userService.deleteUser(req.getParameter("id"));
            }
            else throw new InvalidRequestException("Not a valid path");

        } catch (NullPointerException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(401);    // Unauthorized
        } catch (InvalidRequestException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException e){
            resp.getWriter().write(e.toString());
            resp.setStatus(409);    // Conflict
        }
    }
}