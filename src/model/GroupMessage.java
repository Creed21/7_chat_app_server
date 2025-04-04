/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Korisnik
 */
public class GroupMessage extends Model implements Serializable {
    private static final long serialVersionUID = 739612353426441123L;
    
    private Integer id;
    private Group group;
    private User userFrom;
    private Timestamp timestamp;

    public GroupMessage() {
    }

    public GroupMessage(Integer id, Group group, User userFrom, Timestamp timestamp) {
        this.id = id;
        this.group = group;
        this.userFrom = userFrom;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    
    
}
