
import model.User;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Korisnik
 */
public class DBBrokerTest {
    public static void main(String[] args) {
        
        User u = (User) baza.DBBroker.getInstance().getUserById(1);
        
        System.out.println(u);
    }
}
