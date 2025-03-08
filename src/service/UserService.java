/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import baza.DBBroker;
import java.util.List;
import model.Model;
import model.User;

/**
 *
 * @author Korisnik
 */
public class UserService {
    private DBBroker dBBroker;

    public UserService() {
        dBBroker = DBBroker.getInstance();
    }
    
    public List<Model> getAllUsers() {
        return dBBroker.getAllUsers();
    }
    
}
