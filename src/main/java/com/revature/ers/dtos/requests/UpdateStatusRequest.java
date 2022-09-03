package com.revature.ers.dtos.requests;

public class UpdateStatusRequest {
    private String statusUpdate;
    private String reimbursementId;

    public UpdateStatusRequest() {
    }

    public UpdateStatusRequest(String statusUpdate, String reimbursementId) {
        this.statusUpdate = statusUpdate;
        this.reimbursementId = reimbursementId;
    }

    public String getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(String statusUpdate) {
        this.statusUpdate = statusUpdate;
    }

    public String getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(String reimbursementId) {
        this.reimbursementId = reimbursementId;
    }
}
