package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.services.ReimbursementService;
import com.revature.ers.services.TokenService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReimbursementServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final ReimbursementService reimbursementService;

    public ReimbursementServlet(ObjectMapper objectMapper, TokenService tokenService, ReimbursementService reimbursementService) {
        this.objectMapper = objectMapper;
        this.tokenService = tokenService;
        this.reimbursementService = reimbursementService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
