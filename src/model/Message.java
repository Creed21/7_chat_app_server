/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import model.User;
import java.sql.*;

/**
 *
 * @author Korisnik
 */
public class Message extends Model implements Serializable {
    private static final long serialVersionUID = 739606934944123123L;
    // moglo je i da se koristi kao fromUserID i toUserID kao sto je reprezentacija u samoj tabeli
    // ovo je drugi nacin da se koriste celi objekti
    private int id;
    private User fromUser;
    private User toUser;
    private String message;
    private Timestamp timestamp;

    public Message() {
    }
    public Message(int id, User toUser, String message) {
        this.id = id;
        this.toUser = toUser;
        this.message = message;
    }
    
    public Message(int id, User fromUser, User toUser, String message) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
    }

    public Message(int id, User fromUser, User toUser, String message, Timestamp timestamp) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageRequest{" + "id=" + id + ", fromUser=" + fromUser + ", toUser=" + toUser + ", message=" + message + ", timestamp=" + timestamp + '}';
    }

    
    
}
