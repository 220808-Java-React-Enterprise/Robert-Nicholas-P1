package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.daos.ReimbursementStatusDAO;
import com.revature.ers.daos.ReimbursementTypeDAO;
import com.revature.ers.dtos.requests.ReimbursementRequest;
import com.revature.ers.dtos.responses.ReimbursementResponse;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.utils.custom_exceptions.MethodNotAllowedException;
import com.revature.ers.utils.custom_exceptions.ResourceConflictException;

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
    public List<Reimbursement> getByType(String filter) {
        return reimbursementDAO.getByType(filter);
    }
    public List<Reimbursement> getHistory(Principal principal){
        List<Reimbursement> reimbursementList =  reimbursementDAO.getManagerHistory(principal.getId());
        return reimbursementList;
    }
    public List<Reimbursement> getPending(String filter) {
        List<Reimbursement> reimbursementList = reimbursementDAO.getByStatus(reimbursementStatusDAO.getStatusId(filter));
        return reimbursementList;
    }
    public List<Reimbursement> getByStatus(String filter) {
        return reimbursementDAO.getByStatus(filter);
    }
    public List<Reimbursement> getAll() {
        return reimbursementDAO.getAll();
    }
    public void updateStatus(String statusUpdate, String reimbursementId, String resolverId) {
        if (isValidStatus(statusUpdate)){
            if (isValidId(reimbursementId)){
                if (isPending(reimbursementId)) {
                    reimbursementDAO.updateStatus(reimbursementStatusDAO.getStatusId(statusUpdate), reimbursementId, resolverId);
                }else throw new MethodNotAllowedException("Cannot update processed request");
            }else throw new ResourceConflictException("Not a valid Reimbursement ID");
        }else throw new ResourceConflictException("Not a valid Status");

    }
    public boolean isPending(String reimbursementId) {
        return (reimbursementDAO.getStatusIDfromReimbID(reimbursementId).equals(reimbursementStatusDAO.getStatusId("PENDING")));
    }
    public boolean isValidStatus(String status){
        return  (reimbursementStatusDAO.getStatusId(status) != null);
    }
    public boolean isValidId(String reimbursementId) {
        return (reimbursementDAO.getById(reimbursementId) != null);
    }
}

