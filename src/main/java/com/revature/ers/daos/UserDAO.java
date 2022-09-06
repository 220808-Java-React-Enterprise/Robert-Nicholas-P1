package com.revature.ers.daos;

import com.revature.ers.dtos.responses.UserResponse;
import com.revature.ers.models.User;
import com.revature.ers.utils.custom_exceptions.InvalidSQLException;
import com.revature.ers.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO implements CrudDAO<User>{
    @Override
    public void save(User user){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO ers_users (id, username, email, password, " +
                    "given_name, surname, is_active, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getId()); ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail()); ps.setString(4, user.getPassword());
            ps.setString(5, user.getGivenName()); ps.setString(6, user.getSurName());
            ps.setBoolean(7, user.isActive()); ps.setString(8, user.getRoleId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error trying to save user");
        }
    }

    @Override
    public void update(User obj) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE ers_users SET password = ?, is_active = ? " +
                    "WHERE id = ?");
            ps.setString(1, obj.getPassword()); ps.setBoolean(2, obj.isActive());
            ps.setString(3, obj.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error updating user");
        }
    }

    @Override
    public void delete(String id) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM ers_users WHERE id = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new InvalidSQLException("Error deleting user");
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    public Map<String, UserResponse> getAllResponse() {
        Map<String, UserResponse> ls = new HashMap<>();
        UserResponse response;

        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT ers_users.id, ers_users.username, ers_users.email, " +
                    "ers_users.given_name, ers_users.surname, ers_users.is_active, ers_user_roles.role FROM ers_users " +
                    "LEFT JOIN ers_user_roles on ers_users.role_id = ers_user_roles.id " +
                    "WHERE NOT ers_user_roles.role = 'ADMIN'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                response = new UserResponse(rs.getString("id"), rs.getString("username"),
                        rs.getString("email"), rs.getString("given_name"),
                        rs.getString("surname"), rs.getString("role"),
                        rs.getBoolean("is_active"));
                ls.put(response.getId(), response);
            }
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting list of userResponse");
        }
        return ls;
    }

    public User getById(String id){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new User(rs.getString("id"), rs.getString("username"),
                    rs.getString("email"), rs.getString("password"), rs.getString("given_name"),
                    rs.getString("surname"), rs.getString("role_id"), rs.getBoolean("is_active"));
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting user by id");
        }
        return null;
    }

    public User getUserByUsernameAndPassword(String username, String password){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_users WHERE username = ? AND password = ?");
            ps.setString(1, username); ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return new User(rs.getString("id"), rs.getString("username"),
                    rs.getString("email"), rs.getString("password"),
                    rs.getString("given_name"), rs.getString("surname"),
                    rs.getString("role_id"), rs.getBoolean("is_active"));
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting user by username and password");
        }
        return null;
    }

    public String getUsername(String username){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT (username) FROM ers_users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("username");
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting username");
        }
        return null;
    }

    public String getEmail(String email){
        try (Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT email FROM ers_users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("email");
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting email");
        }
        return null;
    }

    public String getRoleIdByRole(String role){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT id FROM ers_user_roles WHERE role = ?");
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("id");
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting role id by role");
        }
        return null;
    }

    public String getRoleIdByUserId(String userId){
        try(Connection con = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT (role_id) FROM ers_users WHERE id = ?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("role_id");
        } catch (SQLException e){
            throw new InvalidSQLException("Error getting role_id from user id");
        }
        return null;
    }
}
