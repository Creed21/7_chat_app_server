/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import baza.DBBroker;
import controller.Controller;
import static java.lang.String.format;
import java.util.List;
import model.Group;
import model.User;
import requestResponse.Response;
import requestResponse.UserGroupRequest;

/**
 *
 * @author Korisnik
 */
public class UserService {
    private DBBroker dBBroker;
    
    public UserService() {
        dBBroker = DBBroker.getInstance();
    }
    
    public Response logIn(String username, String password) {
        Response response = new Response();
        
        if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
            response.setMessage("Wrong username or password!");
            return response;
        }
        
        User user = dBBroker.login(username, password);
        if(user == null) {
            response.setMessage("Wrong username or password!");
        } else {
            response.setMessage("Welcome " + user.getUsername());
            response.setSuccess(true);
            response.setResult(user);
        }
        return response;
    }
    
    public Response getAllUsers() {
        Response response = new Response();
        List result = dBBroker.getAllUsers();
        response.setResultList(result);
        if(result.isEmpty()) {
            response.setMessage("No users");
        } else {
            response.setSuccess(true);
            response.setMessage(format("Found %d users", result.size()));
        }
        
        return response;
    }
    
    public Response addUserInGroup(UserGroupRequest userGroupRequest) {
        Response response = new Response();
        User user = userGroupRequest.getUser();
        Group group = userGroupRequest.getGroup();
        
        if(user == null || group == null) {
            response.setMessage("Invalid parameters for addUserInGroup");
            return response;
        }
        
        if(dBBroker.addUserInGroup(user.getId(), group.getId())) {
            response.setSuccess(true);
            response.setMessage(format("Successfully added user %s in group %s", user.getUsername(), group.getName()));
        } else {
            response.setMessage(format("Unable to add user %s in group %s", user.getUsername(), group.getName()));
        }
        
        return response;
    }
    
}
