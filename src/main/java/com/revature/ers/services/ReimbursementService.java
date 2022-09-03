package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.daos.ReimbursementStatusDAO;
import com.revature.ers.daos.ReimbursementTypeDAO;
import com.revature.ers.dtos.requests.ReimbursementRequest;
import com.revature.ers.dtos.responses.ReimbursementResponse;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;
import com.revature.ers.dtos.responses.Principal;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import java.util.List;

public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;
    private final ReimbursementTypeDAO reimbursementTypeDAO;
    private final ReimbursementStatusDAO reimbursementStatusDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO, ReimbursementTypeDAO reimbursementTypeDAO, ReimbursementStatusDAO reimbursementStatusDAO) {
        this.reimbursementDAO = reimbursementDAO;
        this.reimbursementTypeDAO = reimbursementTypeDAO;
        this.reimbursementStatusDAO = reimbursementStatusDAO;
    }

    public Reimbursement submit(ReimbursementRequest request, String authorId){
        Reimbursement reimbursement = null;

        reimbursement = new Reimbursement(UUID.randomUUID().toString(), request.getAmount(), new Timestamp(System.currentTimeMillis()),
                request.getDescription(), request.getReceipt(), request.getPaymentId(), authorId,
                reimbursementStatusDAO.getStatusId("PENDING"), reimbursementTypeDAO.getTypeId(request.getType().toUpperCase()));
        reimbursementDAO.save(reimbursement);

        return reimbursement;
    }

    public void updateReimbursement(Reimbursement reimbursement){
        reimbursementDAO.update(reimbursement);
    }

    public String getStatus(String id){
        return reimbursementStatusDAO.getStatus(id);
    }

    public String getTypeId(String type){
        return reimbursementTypeDAO.getTypeId(type);
    }

    public Map<String, Reimbursement> getReimbursementList(String id){
        Map<String, Reimbursement> ls = reimbursementDAO.getAllReimbursementsByAuthorId(id);
        if (ls == null) throw new InvalidRequestException("\nNo reimbursements found");
        return ls;
    }

    public Map<String, ReimbursementResponse> getReimbursementResponseList(String username){
        Map<String, ReimbursementResponse> ls = reimbursementDAO.getAllResponseByUsername(username);
        if (ls == null) throw new InvalidRequestException("\nNo reimbursements found");
        return ls;
    }

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidAmount(String amount){
        if (!amount.matches("^\\$[0-9]+(\\.[0-9][0-9])?$"))
            throw new InvalidRequestException("\nInvalid amount format");
        return true;
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

