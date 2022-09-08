package com.revature.ers.daos;

import com.revature.ers.dtos.responses.ReimbursementResponse;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.utils.custom_exceptions.InvalidSQLException;
import com.revature.ers.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReimbursementDAO implements CrudDAO<Reimbursement> {
    @Override
    public void save(Reimbursement obj){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO ers_reimbursements (id, amount, submitted, description, " +
                    "payment_id, author_id, status_id, type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId()); ps.setFloat(2, obj.getAmount());
            ps.setTimestamp(3, obj.getSubmitted()); ps.setString(4, obj.getDescription());
            ps.setString(5, obj.getPaymentId()); ps.setString(6, obj.getAuthorId());
            ps.setString(7, obj.getStatusId()); ps.setString(8, obj.getTypeId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save reimbursements");
        }
    }

    @Override
    public void update(Reimbursement obj) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE ers_reimbursements SET amount = ?, " +
                    "description = ?, payment_id = ?, type_id = ?, submitted = ? WHERE id = ?");
            ps.setFloat(1, obj.getAmount()); ps.setString(2, obj.getDescription());
            ps.setString(3, obj.getPaymentId()); ps.setString(4, obj.getTypeId());
            ps.setTimestamp(5, obj.getSubmitted()); ps.setString(6, obj.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error updating reimbursement");
        }
    }


    @Override

    public void delete(String id) {

    }


    @Override
    public List<Reimbursement> getAll() {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements");

            ResultSet rs = ps.executeQuery();
            List<Reimbursement> reimbursementList = new ArrayList<>();
            while (rs.next()){
                reimbursementList.add(new Reimbursement(rs.getString("id"), rs.getFloat("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"),rs.getString("author_id"),rs.getString("status_id"),
                        rs.getString("type_id")));
            }
            return reimbursementList;
        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [getByType]");
        }
    }

    public List<Reimbursement> getByType(String filter) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements where type_id = ?");
            ps.setString(1,filter);
            ResultSet rs = ps.executeQuery();
            List<Reimbursement> reimbursementList = new ArrayList<>();
            while (rs.next()){
                reimbursementList.add(new Reimbursement(rs.getString("id"), rs.getFloat("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"),rs.getString("author_id"),rs.getString("status_id"),
                        rs.getString("type_id")));
            }
            return reimbursementList;
        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [getByType]");
        }
    }

    public List<Reimbursement> getByStatus(String filter) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements where status_id = ?");
            ps.setString(1,filter);
            ResultSet rs = ps.executeQuery();
            List<Reimbursement> reimbursementList = new ArrayList<>();
            while (rs.next()){
                reimbursementList.add(new Reimbursement(rs.getString("id"), rs.getFloat("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"),rs.getString("author_id"),rs.getString("status_id"),
                        rs.getString("type_id")));
            }
            return reimbursementList;
        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [getByStatus]");
        }catch (NullPointerException e){
            throw new RuntimeException("Returning Null");
        }
    }


    public List<Reimbursement> getManagerHistory(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements where resolver_id = ?");
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            List<Reimbursement> reimbursementList = new ArrayList<>();
            while (rs.next()){
                reimbursementList.add(new Reimbursement(rs.getString("id"), rs.getFloat("amount"), rs.getTimestamp("submitted"),
                        rs.getTimestamp("resolved"), rs.getString("description"),rs.getString("author_id"),rs.getString("status_id"),
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
            ps.executeUpdate();

            System.out.println("Status Updated");

        } catch (SQLException e) {
            throw new InvalidSQLException("Exception: Problem retrieving from database [updateStatus]");
        }
    }

    public Map<String, Reimbursement> getAllReimbursementsByAuthorId(String username){
        Map<String, Reimbursement> ls = new HashMap<>();
        Reimbursement reimb;

        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE author_id = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                reimb = new Reimbursement(rs.getString("id"), rs.getString("description"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id"), rs.getFloat("amount"),
                        rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getBlob("receipt"));
                ls.put(rs.getString("id"), reimb);
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting reimbursements by username");
        }
        return ls;
    }

    public Map<String, ReimbursementResponse> getAllResponseByUsername(String username){
        Map<String, ReimbursementResponse> ls = new HashMap<>();
        ReimbursementResponse reimb;

        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT ers_reimbursements.id, ers_reimbursements.amount, " +
                    "ers_reimbursements.submitted, ers_reimbursements.resolved, ers_reimbursements.description, ers_reimbursements.receipt, " +
                    "ers_reimbursements.payment_id, author.username as author, resolver.username as resolver, ers_reimbursement_statuses.status, " +
                    "ers_reimbursement_types.type " +
                    "FROM ers_reimbursements " +
                    "LEFT JOIN ers_users author on ers_reimbursements.author_id = author.id " +
                    "LEFT JOIN ers_users resolver on ers_reimbursements.resolver_id = resolver.id " +
                    "JOIN ers_reimbursement_statuses on ers_reimbursements.status_id = ers_reimbursement_statuses.id " +
                    "JOIN ers_reimbursement_types on ers_reimbursements.type_id = ers_reimbursement_types.id " +
                    "WHERE author.username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                reimb = new ReimbursementResponse(rs.getString("id"), rs.getFloat("amount"),
                        rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"),
                        rs.getBlob("receipt"), rs.getString("payment_id"), rs.getString("author"),
                        rs.getString("resolver"), rs.getString("status"), rs.getString("type"));
                ls.put(rs.getString("id"), reimb);
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting reimbursement response by username");
        }
        return ls;
    }

    public Reimbursement getById(String reimbursementId) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursements WHERE id = ?");
            ps.setString(1, reimbursementId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                return new Reimbursement(rs.getString("id"), rs.getString("description"),
                        rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"),
                        rs.getString("status_id"), rs.getString("type_id"), rs.getFloat("amount"),
                        rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getBlob("receipt"));

            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting reimbursements by username");
        }
        return null;
    }

    public String getStatusIDfromReimbID(String reimbursementId) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT status_id FROM ers_reimbursements WHERE id = ?");
            ps.setString(1, reimbursementId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                return rs.getString("status_id");

            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting status Id");
        }
        return null;
    }
}
