package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.daos.ReimbursementStatusDAO;
import com.revature.ers.daos.ReimbursementTypeDAO;
import com.revature.ers.dtos.requests.ReimbursementRequest;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.UUID;

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

    // Pre:
    // Post:
    // Purpose:
    public boolean isValidAmount(String amount){
        if (!amount.matches("^\\$[0-9]+(\\.[0-9][0-9])?$"))
            throw new InvalidRequestException("\nInvalid amount format");
        return true;
    }
}