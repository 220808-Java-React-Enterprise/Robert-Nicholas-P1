package com.revature.ers.daos;

import com.revature.ers.models.Reimbursement;
import com.revature.ers.utils.database.ConnectionFactory;
import com.revature.ers.utils.database.custom_exceptions.InvalidSQLException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<Reimbursement> {
    @Override
    public void save(Reimbursement obj){

    }

    @Override
    public void update(Reimbursement obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Reimbursement> getAll() {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM testnicholas.ers_reimbursements");

            ResultSet rs = ps.executeQuery();
            List<Reimbursement> reimbursementList = new ArrayList<>();
            while (rs.next()){
                reimbursementList.add(new Reimbursement(rs.getString("id"), rs.getFloat("amount"), rs.getDate("submitted"),
                        rs.getDate("resolved"), rs.getString("description"),rs.getString("author_id"),rs.getString("status_id"),
                        rs.getString("type_id")));
            }
            return reimbursementList;
        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [getByType]");
        }
    }

    public List<Reimbursement> getByType(String filter) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM testnicholas.ers_reimbursements where type_id = ?");
            ps.setString(1,filter);
            ResultSet rs = ps.executeQuery();
            List<Reimbursement> reimbursementList = new ArrayList<>();
            while (rs.next()){
                reimbursementList.add(new Reimbursement(rs.getString("id"), rs.getFloat("amount"), rs.getDate("submitted"),
                        rs.getDate("resolved"), rs.getString("description"),rs.getString("author_id"),rs.getString("status_id"),
                        rs.getString("type_id")));
            }
            return reimbursementList;
        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [getByType]");
        }
    }

    public List<Reimbursement> getByStatus(String filter) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM testnicholas.ers_reimbursements where status_id = ?");
            ps.setString(1,filter);
            ResultSet rs = ps.executeQuery();
            List<Reimbursement> reimbursementList = new ArrayList<>();
            while (rs.next()){
                reimbursementList.add(new Reimbursement(rs.getString("id"), rs.getFloat("amount"), rs.getDate("submitted"),
                        rs.getDate("resolved"), rs.getString("description"),rs.getString("author_id"),rs.getString("status_id"),
                        rs.getString("type_id")));
            }
            return reimbursementList;
        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [getByStatus]");
        }
    }
}
