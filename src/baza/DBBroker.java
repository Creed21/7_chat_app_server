/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import model.Model;
import model.Message;

/**
 *
 * @author USER
 */
public class DBBroker {
    
    private static DBBroker instance = new DBBroker();
    private Connection connection;
    
    private DBBroker() {
        try {
            String url = "jdbc:mysql://localhost:3306/chat_app";
            connection = DriverManager.getConnection(url, "root", "");
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Konekcija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DBBroker getInstance() {
        return instance;
    }
    
    
    public User login(String username, String password) {
        String query = "select * from user where user_name = ? and password = ?";
        User user = null;
        try(PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ps.setString(2, password);
            System.out.println("login query: " + query);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"), 
                        rs.getString("last_name"), 
                        rs.getString("user_name"), 
                        rs.getString("email"), 
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }
    
    public Model getUserById(int id) {
        User user = null;
        String query = "select * from user where id = ?";
        
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"), 
                        rs.getString("last_name"), 
                        rs.getString("user_name"), 
                        rs.getString("email")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    public List<Model> getAllUsers() {
        String query = "select * from user";
        List<Model> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                users.add( 
                    new User(
                        rs.getInt("id"),
                        rs.getString("name"), 
                        rs.getString("last_name"), 
                        rs.getString("user_name"), 
                        rs.getString("email")
                    )
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
    } 

    public List<Model> readMessages(User userFrom, int userToId) {
        List<Model> messages = new ArrayList<>();
        String query = "select 	* \n" +
                        "from message\n" +
                        "where	id_user_from = ?\n" +
                        "	and id_user_to = ?\n" +
                        "	or  id_user_from = ?\n" +
                        "	AND id_user_to = ?";
        
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            int userFromId = userFrom.getId();
            
            ps.setInt(1, userFromId);
            ps.setInt(2, userToId);
            ps.setInt(3, userFromId);
            ps.setInt(4, userToId);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                User userTo = (User) getUserById(rs.getInt("id_user_to"));
                
                messages.add(
                        new Message(
                                rs.getInt("id"),
                                userFrom,
                                userTo,
                                rs.getString("message"),
                                rs.getTimestamp("time_sent")
                        )
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return messages;
    }

}
