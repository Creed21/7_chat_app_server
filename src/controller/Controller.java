/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author Korisnik
 */
// kontroler moze da cuva ulogavane korisnike
// i recimo da ima kick operaciju
public class Controller {
    
    private static Controller instance;
    private static List<User> loggedUsers;

    private Controller() {
        loggedUsers = new ArrayList<>();
    }
    
    // this is not thread safe!!
    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
    }
    
    // add logged user
    public void addLoggedUser(User user) {
        loggedUsers.add(user);
    }
    
    // kick user
    public boolean kickUser(User user) {
        return loggedUsers.remove(user);
    }
    public boolean kickUser(String userName) {
        return loggedUsers.removeIf(u -> u.getUsername().equals(userName));
    }
    

    public List<User> getLoggedUsers() {
        return loggedUsers;
    }
    
    
}
