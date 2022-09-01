package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.ReimbursementRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.services.ReimbursementService;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;
import com.revature.ers.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReimbursementServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final UserService userService;
    private final ReimbursementService reimbursementService;

    public ReimbursementServlet(ObjectMapper mapper, TokenService tokenService, UserService userService, ReimbursementService reimbursementService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.userService = userService;
        this.reimbursementService = reimbursementService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try {
            if (principal.getRoleId().equals(userService.getRoleIdByUserId(principal.getId()))){
                String role = userService.getRoleByRoleId(principal.getRoleId());

                if (role.equals("MANAGER")){
                    resp.getWriter().write("<h1>Manager</h1>");
                }
                else if (role.equals("EMPLOYEE")){

                }
            }

        } catch (NullPointerException e){
            resp.setStatus(401);    // Unauthorized
        } catch (InvalidRequestException e){
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException e){
            resp.setStatus(409);    // Conflict
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try{
            if (principal.getRoleId().equals(userService.getRoleIdByUserId(principal.getId()))){
                String role = userService.getRoleByRoleId(principal.getRoleId());

                if (role.equals("MANAGER")){
                    resp.getWriter().write("<h1>Manager</h1>");
                }
                else if (role.equals("EMPLOYEE")){
                    ReimbursementRequest request = mapper.readValue(req.getInputStream(), ReimbursementRequest.class);
                    Reimbursement reimbursement = reimbursementService.submit(request, principal.getId());
                    resp.setStatus(200);
                    resp.getWriter().write(reimbursement.toString());
                }
            }

        } catch (NullPointerException e){
            resp.setStatus(401);    // Unauthorized
        } catch (InvalidRequestException e){
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException e){
            resp.setStatus(409);    // Conflict
        }
    }


}
