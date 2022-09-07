package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.revature.ers.dtos.requests.ReimbursementRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.dtos.responses.ReimbursementResponse;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.services.ReimbursementService;
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
import java.sql.Timestamp;
import java.util.Map;

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
        // Get token from header
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);
        System.out.println("Do get");
        try {
            // Verify token
            // If role is manager
            System.out.println(principal.getRole());
            if (principal.getRole().equals("MANAGER")){
                System.out.println("Finance Manager Authorized");
                String[] path = req.getRequestURI().split("/");
                resp.getWriter().write("<h1>Manager</h1>");

            }
            // If role is employee
            else if (principal.getRole().equals("EMPLOYEE")){
                // Get list of employee reimbursements
                Map<String, ReimbursementResponse> ls = reimbursementService.getReimbursementResponseList(principal.getUsername());
                String id;

                // If id is not null print detailed reimbursement
                if ((id = req.getParameter("id")) != null)
                    resp.getWriter().write(ls.get(id).toStringDetailed());
                    // Print list
                else{
                    resp.getWriter().write("<ul>");
                    for (ReimbursementResponse r : ls.values()){
                        resp.getWriter().write("<a href=\"http://localhost:8080/ers/reimb?id=" + r.getId() + "\">Details</a>");
                        resp.getWriter().write("<li>" + r + "</li>");
                    }
                    resp.getWriter().write("</ul>");
                }

                resp.setStatus(200);
            }
            else throw new AuthenticationException("Unauthorized");

        }  catch (InvalidRequestException e){
            resp.getWriter().write(e.getMessage());
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException e){
            resp.getWriter().write(e.getMessage());
            resp.setStatus(409);    // Conflict
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try{
            if (principal.getRole().equals("MANAGER")){
                resp.getWriter().write("<h1>Manager</h1>");
            }
            else if (principal.getRole().equals("EMPLOYEE")){
                ReimbursementRequest request = mapper.readValue(req.getInputStream(), ReimbursementRequest.class);

                // Submit new reimbursement
                reimbursementService.submit(request, principal.getId());

                resp.getWriter().write("Request Submitted");
                resp.setStatus(200);
            }
            else throw new AuthenticationException("Unauthorized");

        } catch (NullPointerException e){
            resp.getWriter().write(e.getMessage());
            resp.setStatus(401);    // Unauthorized
        } catch (InvalidRequestException e){
            resp.getWriter().write(e.getMessage());
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException | InvalidFormatException e){
            resp.getWriter().write(e.getMessage());
            resp.setStatus(409);    // Conflict
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try{
            if (principal.getRole().equals("MANAGER")){
                resp.getWriter().write("<h1>Manager</h1>");
            }
            else if (principal.getRole().equals("EMPLOYEE")){
                ReimbursementRequest request = mapper.readValue(req.getInputStream(), ReimbursementRequest.class);
                String id;

                // Update reimbursement
                if ((id = req.getParameter("id")) != null) {
                    Map<String, Reimbursement> ls = reimbursementService.getReimbursementList(principal.getId());
                    if (reimbursementService.getStatus(ls.get(id).getStatusId()).equals("PENDING")){
                        Reimbursement reimbursement = ls.get(id);
                        reimbursement.setAmount(request.getAmount());
                        reimbursement.setDescription(request.getDescription());
                        reimbursement.setReceipt(request.getReceipt());
                        reimbursement.setPaymentId(request.getPaymentId());
                        reimbursement.setSubmitted(new Timestamp(System.currentTimeMillis()));
                        reimbursement.setTypeId(reimbursementService.getTypeId(request.getType().toUpperCase()));
                        reimbursementService.updateReimbursement(reimbursement);
                    }
                }
                else throw new ResourceConflictException("No id found");
            }
            else throw new AuthenticationException("Unauthorized");
        }catch (NullPointerException e){
            resp.getWriter().write(e.getMessage());
            resp.setStatus(401);    // Unauthorized
        } catch (InvalidRequestException e){
            resp.getWriter().write(e.getMessage());
            resp.setStatus(404);    // Bad Request
        } catch (ResourceConflictException | InvalidFormatException e){
            resp.getWriter().write(e.getMessage());
            resp.setStatus(409);    // Conflict
        }
    }
}
