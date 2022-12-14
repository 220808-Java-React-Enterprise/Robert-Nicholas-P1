package com.revature.ers.daos;

import com.revature.ers.models.ReimbursementTypes;
import com.revature.ers.utils.custom_exceptions.InvalidSQLException;
import com.revature.ers.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementTypeDAO implements CrudDAO<ReimbursementTypes> {
    @Override
    public void save(ReimbursementTypes obj){

    }

    @Override
    public void update(ReimbursementTypes obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<ReimbursementTypes> getAll() {
        return null;
    }

    public String getTypeId(String type){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT id FROM ers_reimbursement_types WHERE type = ?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("id");
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting id from type");
        }
        return null;
    }
}
