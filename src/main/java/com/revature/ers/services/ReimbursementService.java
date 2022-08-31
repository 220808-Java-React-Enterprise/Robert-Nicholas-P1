package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
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

    public List<Reimbursement> getByStatus(String filter) {
        return reimbursementDAO.getByStatus(filter);
    }

    public List<Reimbursement> getAll() {
        return reimbursementDAO.getAll();
    }
}