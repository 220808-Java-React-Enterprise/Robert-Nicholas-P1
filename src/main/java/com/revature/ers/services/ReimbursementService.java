package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.Reimbursement;

import java.util.List;

public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    public List<Reimbursement> getByType(String filter) {
        return reimbursementDAO.getByType(filter);
    }
    public String getHistory(Principal principal){
        List<Reimbursement> reimbursementList =  reimbursementDAO.getManagerHistory(principal.getId());
        String html =
                "<table>" +
                        "<tr>" +
                        "<th>ID</th>" +
                        "<th>Amount</th>" +
                        "<th>Submitted</th>" +
                        "<th>Author</th>" +
                        "<th>Type</th>" +
                        "</tr>";

        for (Reimbursement n : reimbursementList){
            html +=
                    "<tr>" +
                            "<th>" + n.getId() + "</th>" +
                            "<th>" + n.getAmount() + "</th>" +
                            "<th>" + n.getSubmitted() + "</th>" +
                            "<th>" + n.getAuthor() + "</th>" +
                            "<th>" + n.getTypeId() + "</th>" +
                            "</tr>";
        }

        html +=
                "</table>";

        return html;
    }
    public String getPendingHtml(String filter) {
        List<Reimbursement> reimbursementList = reimbursementDAO.getByStatus(filter);
        String html =
                "<table>" +
                    "<tr>" +
                        "<th>ID</th>" +
                        "<th>Amount</th>" +
                        "<th>Submitted</th>" +
                        "<th>Author</th>" +
                        "<th>Type</th>" +
                    "</tr>";

        for (Reimbursement n : reimbursementList){
            html +=
                    "<tr>" +
                        "<th>" + n.getId() + "</th>" +
                        "<th>" + n.getAmount() + "</th>" +
                        "<th>" + n.getSubmitted() + "</th>" +
                        "<th>" + n.getAuthor() + "</th>" +
                        "<th>" + n.getTypeId() + "</th>" +
                    "</tr>";
        }

        html +=
                "</table>";

        return html;
    }

    public List<Reimbursement> getByStatus(String filter) {
        return reimbursementDAO.getByStatus(filter);
    }

    public List<Reimbursement> getAll() {
        return reimbursementDAO.getAll();
    }

    public void updateStatus(String statusUpdate, String reimbursementId, String resolverId) {
        reimbursementDAO.updateStatus(statusUpdate, reimbursementId, resolverId);
    }

    public String toHTTPTable (List<String> header,List<String> rows){
        String http = "";

        return http;
    }
}

