package com.revature.ers.daos;

import com.revature.ers.models.ReimbursementStatus;
import com.revature.ers.utils.custom_exceptions.InvalidSQLException;
import com.revature.ers.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementStatusDAO implements CrudDAO<ReimbursementStatus> {
    @Override
    public void save(ReimbursementStatus obj) {

    }

    @Override
    public void update(ReimbursementStatus obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<ReimbursementStatus> getAll() {
        return null;
    }

    public String getStatus(String id){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT status FROM ers_reimbursement_statuses WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("status");
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting status from id");
        }
        return null;
    }

    public String getStatusId(String status){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT id FROM ers_reimbursement_statuses WHERE status = ?");
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("id");
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting id from status");
        }
        return null;
    }
}
