package com.revature.ers.daos;

import com.revature.ers.models.Reimbursement;

import com.revature.ers.utils.database.ConnectionFactory;
import com.revature.ers.utils.database.custom_exceptions.InvalidSQLException;

import java.io.IOException;
import java.sql.*;
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


    public List<Reimbursement> getManagerHistory(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM testnicholas.ers_reimbursements where resolver_id = ?");
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            List<Reimbursement> reimbursementList = new ArrayList<>();
            while (rs.next()){
                reimbursementList.add(new Reimbursement(rs.getString("id"), rs.getFloat("amount"), rs.getDate("submitted"),
                        rs.getDate("resolved"), rs.getString("description"),rs.getString("author_id"),rs.getString("status_id"),
                        rs.getString("type_id")));
            }
            return reimbursementList;
        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [getManagerHistory]");
        }
    }

    public void updateStatus(String statusUpdate, String reimbursementId, String resolverId) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("update ers_reimbursements \n" +
                    "\tset\tresolved = ?,\n" +
                    "\t\tresolver_id = ?,\n" +
                    "\t\tstatus_id = ?\n" +
                    "\twhere id = ?");
            ps.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            ps.setString(2, resolverId);
            ps.setString(3, statusUpdate);
            ps.setString(4,reimbursementId);
            ResultSet rs = ps.executeQuery();

            System.out.println("Status Updated");

        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [updateStatus]");
        }
    }
}
