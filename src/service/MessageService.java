/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import baza.DBBroker;
import java.util.List;
import model.User;
import model.Message;
import model.Model;
import requestResponse.Response;

/**
 *
 * @author Korisnik
 */
public class MessageService {
    private DBBroker dBBroker;

    public MessageService() {
        dBBroker = DBBroker.getInstance();
    }
    
    public Response readMessages(User fromUser, User toUser) {
        Response response = new Response();
        List<Model> messages = dBBroker.readMessages(fromUser, toUser.getId());
        
        if(messages.isEmpty()) {
            response.setMessage("No messages.");
        } else {
            response.setSuccess(true);
            response.setMessage(String.format("Message count: %s", messages.size()));
        }
        
        response.setResultList(messages);
        
        return response;
    }

    public Response sendMessage(Message sendMessage) {
//        dBBroker.
        return null;
    }
    
}
