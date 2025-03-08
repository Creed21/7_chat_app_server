/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
public class User extends Model implements Serializable  {
    private static final long serialVersionUID = 7396069349441505257L;
    
    private int id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User))
            return false;
        
        User userToCheck = (User) obj;
        
        return userToCheck.id == this.id 
                || userToCheck.username.equals(this.username) 
                || userToCheck.email.equals(this.email);
    }

    public User() {
    }
    
    public User(int id, String name, String lastName, String username, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }
    
    public User(int id, String name, String lastName, String username, String email, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", lastName=" + lastName + ", username=" + username + ", email=" + email + ", password=" + password + '}';
    }
    
    
    
}
