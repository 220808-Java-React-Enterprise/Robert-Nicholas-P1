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
import java.text.NumberFormat;
import java.util.Locale;
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

    // Pre: An employee makes a reimbursement request
    // Post: A request is submitted to the db
    // Purpose: To save a request to the db
    public Reimbursement submit(ReimbursementRequest request, String authorId){
        Reimbursement reimbursement = null;

        reimbursement = new Reimbursement(UUID.randomUUID().toString(), request.getAmount(), new Timestamp(System.currentTimeMillis()),
                request.getDescription(), request.getReceipt(), request.getPaymentId(), authorId,
                reimbursementStatusDAO.getStatusId("PENDING"), reimbursementTypeDAO.getTypeId(request.getType().toUpperCase()));

        reimbursementDAO.save(reimbursement);

        return reimbursement;
    }

    // Pre: An employee is trying to update a pending request
    // Post: A request has been updated
    // Purpose: To update an employees existing pending requests
    public void updateReimbursement(Reimbursement reimbursement){
        reimbursementDAO.update(reimbursement);
    }

    // Pre: A status id is passed in
    // Post: The status corresponding to the id given is returned
    // Purpose: To get the status from the status id
    public String getStatus(String id){
        return reimbursementStatusDAO.getStatus(id);
    }

    // Pre: A type is passed in
    // Post: The type id corresponding to the type given is returned
    // Purpose: To get the types id from the type given
    public String getTypeId(String type){
        return reimbursementTypeDAO.getTypeId(type);
    }

    // Pre: User is attempting to get a list of Reimbursement
    // Post: A list of reimbursements is returned
    // Purpose: To get full reimbursement details
    public Map<String, Reimbursement> getReimbursementList(String id){
        Map<String, Reimbursement> ls = reimbursementDAO.getAllReimbursementsByAuthorId(id);
        if (ls == null) throw new InvalidRequestException("\nNo reimbursements found");
        return ls;
    }

    // Pre: User is attempting to get their Reimbursement
    // Post: A list of that users Reimbursements is returned
    // Purpose: To get a list of a particular user reimbursements
    public Map<String, ReimbursementResponse> getReimbursementResponseList(String username){
        Map<String, ReimbursementResponse> ls = reimbursementDAO.getAllResponseByUsername(username);
        if (ls == null) throw new InvalidRequestException("\nNo reimbursements found");
        return ls;
    }

    public List<Reimbursement> getByType(String filter) {
        return reimbursementDAO.getByType(filter);
    }
    public List<Reimbursement> getHistory(Principal principal){
        List<Reimbursement> reimbursementList =  reimbursementDAO.getManagerHistory(principal.getId());
        return reimbursementList;
    }
    public List<Reimbursement> getPending(String filter) {
        List<Reimbursement> reimbursementList = reimbursementDAO.getByStatus(filter);
        return reimbursementList;
    }

    public List<Reimbursement> getByStatus(String filter) {
        return reimbursementDAO.getByStatus(filter);
    }

    public List<Reimbursement> getAll() {
        return reimbursementDAO.getAll();
    }

    public void updateStatus(String statusUpdate, String reimbursementId, String resolverId) {
        reimbursementDAO.updateStatus(reimbursementStatusDAO.getStatusId(statusUpdate), reimbursementId, resolverId);
    }


}

