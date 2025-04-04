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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.Group;
import model.GroupMessage;
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
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public synchronized Boolean addUserInGroup(int userId, int groupId) {
        // TODO za domaci proveriti prvo da li korisnik postoji vec u grupi
        
        String query = "insert into users_groups(user_id, group_id)\n" +
                        "values(?, ?)";
        
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, groupId);
            
            int result = ps.executeUpdate();

            if(result == 1)
                connection.commit();
            
            return result == 1;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public List<Model> getAllGroups() {
        String query = "select * from groups";
        List<Model> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                users.add( 
                    new Group(
                        rs.getInt("id"),
                        rs.getString("name")
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
                        "	or  id_user_to = ?\n" +
                        "	AND id_user_from = ?";
        
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            int userFromId = userFrom.getId();
            
            ps.setInt(1, userFromId);
            ps.setInt(2, userToId);
            ps.setInt(3, userFromId);
            ps.setInt(4, userToId);
            
            System.out.println("DB messages query: " + query+ " userFromId:"+userFromId+ " userToId:"+userToId);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                User userTo = (User) getUserById(rs.getInt("id_user_to"));
                int idUserToQueryRes = rs.getInt("id_user_to");
                
                messages.add(
                        idUserToQueryRes == userToId ?
                        new Message(
                                rs.getInt("id"),
                                userFrom,
                                userTo,
                                rs.getString("message"),
                                rs.getTimestamp("time_sent")
//                        ));
                        ) :
                         new Message(
                                rs.getInt("id"),
                                userTo,
                                userFrom,
                                rs.getString("message"),
                                rs.getTimestamp("time_sent")
                        )       
                );
            }
            
            System.out.println("DB messages: "+messages);
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return messages;
    }
    
    public synchronized Boolean sendMessage(Message message) {
                            // message(id, id_user_from, id_user_to, message+text, timestamp
        String query = "insert into message(id_user_from, id_user_to, message, time_sent)\n" +
                        "values(?, ?, ?, ?)";
        
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, message.getFromUser().getId());
            ps.setInt(2, message.getToUser().getId());
            ps.setString(3, message.getMessage());
            
            Timestamp timestamp = 
                    message.getTimestamp() != null ? 
                    message.getTimestamp() : 
                    Timestamp.valueOf(LocalDateTime.now());
            
            ps.setTimestamp(4, message.getTimestamp());
            
            int result = ps.executeUpdate();

            if(result == 1)
                connection.commit();
            
            return result == 1;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public synchronized Boolean sendGroupMessage(GroupMessage groupMessage) {
        String query = "insert into grou_messages(id_group, id_message, id_user_from, message, time_sent)\n" +
                        "values(?, ?, ?, ?)";
        
        // pronadji poslednji id grupne poruke
        
        // postavi vrednosti za novu poruku u tabeli
        
        return false;
    }

    public List<Model> readGroupMessages(Integer id) {
        String query = "select * from group_messages where id_group = ?";
        List<Model> messages = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                GroupMessage gm = new GroupMessage();
                gm.setId(rs.getInt("id_group"));
                gm.setGroup((Group) getGroupById(id));
                gm.setUserFrom((User) getUserById(rs.getInt("id_from_user")));
                gm.setTimestamp(rs.getTimestamp("time_sent"));
                
                messages.add(gm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return messages;
    }
    
    public Model getGroupById(Integer id) {
        String query = "select * from groups where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                return new Group(
                        id,
                        rs.getString("name")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
