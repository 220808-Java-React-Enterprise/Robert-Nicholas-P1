package com.revature.ers.daos;

import com.revature.ers.models.UserRole;
import com.revature.ers.utils.custom_exceptions.InvalidSQLException;
import com.revature.ers.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRoleDAO implements CrudDAO<UserRole>{
    @Override
    public void save(UserRole obj){

    }

    @Override
    public void update(UserRole obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<UserRole> getAll() {
        return null;
    }

    public String getRoleById(String id){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT role FROM ers_user_roles WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("role");
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting role from user_roles");
        }
        return null;
    }
}
