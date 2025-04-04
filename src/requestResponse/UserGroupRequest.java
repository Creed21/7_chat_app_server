/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestResponse;

import model.Group;
import model.User;

/**
 *
 * @author Korisnik
 */
public class UserGroupRequest {
    private static final long serialVersionUID = 7123213325052L;
    private User user;
    private Group group;

    public UserGroupRequest() {
    }

    public UserGroupRequest(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
    
}
