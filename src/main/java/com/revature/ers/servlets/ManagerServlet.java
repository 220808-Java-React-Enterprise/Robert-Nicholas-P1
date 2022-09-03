package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.UpdateStatusRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.services.ReimbursementService;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.utils.database.custom_exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManagerServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final ReimbursementService reimbursementService;
    private final TokenService tokenService;
    private final UserService userService;

    public ManagerServlet(ObjectMapper mapper, TokenService tokenService,  UserService userService, ReimbursementService reimbursementService) {
        this.mapper = mapper;
        this.reimbursementService = reimbursementService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost Executing");
        String token = req.getHeader("Authorization");

        Principal principal = tokenService.extractRequesterDetails(token);


        String[] path = req.getRequestURI().split("/");

        try {
            if (principal.getRoleId().equals("MANAGER")) {
                System.out.println("Manager Role authenticated");
                resp.setContentType("application/json");
                if (path[3].equals("updateStatus")){
                    System.out.println("Updating Status");

                    UpdateStatusRequest updateStatusRequest = mapper.readValue(req.getInputStream(), UpdateStatusRequest.class);
                    reimbursementService.updateStatus(updateStatusRequest.getStatusUpdate(),updateStatusRequest.getReimbursementId(), principal.getId());
                    resp.getWriter().write(mapper.writeValueAsString("Statuses Updated"));
                } else if (path[3].equals("blank")) {

                }else if (path[3].equals("blank2")){

                }

            } else {
                resp.setStatus(403); // FORBIDDEN
            }
        } catch (NullPointerException e) {
            resp.setStatus(401); // UNAUTHORIZED
        } catch (InvalidRequestException e) {
            resp.setStatus(404);
        }

        return;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet Executing. Getting parameters");
        String token = req.getHeader("Authorization");

        Principal principal = tokenService.extractRequesterDetails(token);


        String[] path = req.getRequestURI().split("/");

        try {
            if (principal.getRoleId().equals("MANAGER")) {
                System.out.println("Manager Role authenticated");
                resp.setContentType("application/json");
                if (path[3].equals("viewreimb")){
                    String fType = req.getParameter("ftype");
                    String filter = req.getParameter("filter");
                    System.out.println(fType + filter);
                    if (fType.equals("TYPE")){
                        System.out.println("Filtering by reimbursement type");
                        resp.getWriter().write(mapper.writeValueAsString(reimbursementService.getPendingHtml(filter)));
                    }else if (fType.equals("STATUS")){
                        System.out.println("Filtering by reimbursement status");
                        resp.getWriter().write(mapper.writeValueAsString(reimbursementService.getByStatus(filter).toString()));
                    }
                } else if (path[3].equals("viewreimball")) {
                    System.out.println("Viewing all reimbursements");
                    resp.getWriter().write(mapper.writeValueAsString(reimbursementService.getAll().toString()));
                }else if (path[3].equals("viewPending")){
                    System.out.println("Filtering by reimbursement type");
                    resp.getWriter().write(mapper.writeValueAsString(reimbursementService.getPendingHtml("PENDING")));
                }else if (path[3].equals("viewHistory")){
                    System.out.println("Showing manager resolution history");
                    resp.getWriter().write(mapper.writeValueAsString(reimbursementService.getHistory(principal)));
                }

            } else {
                resp.setStatus(403); // FORBIDDEN
            }
        } catch (NullPointerException e) {
            resp.setStatus(401); // UNAUTHORIZED
        } catch (InvalidRequestException e) {
            resp.setStatus(404);
        }

        return;
    }
}