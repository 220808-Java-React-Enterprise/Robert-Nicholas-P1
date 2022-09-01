package com.revature.ers.daos;

import com.revature.ers.models.Reimbursement;
import com.revature.ers.utils.custom_exceptions.InvalidSQLException;
import com.revature.ers.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<Reimbursement> {
    @Override
    public void save(Reimbursement obj){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO ers_reimbursements (id, amount, submitted, description, " +
                    "payment_id, author_id, status_id, type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId()); ps.setFloat(2, obj.getAmount());
            ps.setTimestamp(3, obj.getSubmitted());
            ps.setString(4, obj.getDescription());
            ps.setString(5, obj.getPaymentId()); ps.setString(6, obj.getAuthorId());
            ps.setString(7, obj.getStatusId());
            ps.setString(8, obj.getTypeId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save reimbursements");
        }
    }

    @Override
    public void update(Reimbursement obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Reimbursement> getAll() {
        return null;
    }
}
