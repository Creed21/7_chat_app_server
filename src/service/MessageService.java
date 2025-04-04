/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import baza.DBBroker;
import static java.lang.String.format;
import java.util.List;
import model.Group;
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
    private Response response;
    
    public MessageService() {
        dBBroker = DBBroker.getInstance();
    }
    
    public Response readMessages(User fromUser, User toUser) {
        response = new Response();
        List<Model> messages = dBBroker.readMessages(fromUser, toUser.getId());
        
        if(messages.isEmpty()) {
            response.setMessage("No messages.");
        } else {
            response.setSuccess(true);
            response.setMessage(format("Message count: %s", messages.size()));
        }
        
        response.setResultList(messages);
        
        return response;
    }

    public Response sendMessage(Message sendMessage) {
        response = new Response();
        
        User fromUser = sendMessage.getFromUser();
        User toUser = sendMessage.getToUser();
        
        if(fromUser == null || toUser == null) {
            response.setMessage("Invalid send message request");
            return response;
        }
        
        if(dBBroker.sendMessage(sendMessage)) {
            response.setSuccess(true);
            response.setMessage("Successfully sent message");
        }

        return response;
    }
    
    public Response readGroupMessages(Group group) {
        response = new Response();
        if(group == null) {
            response.setMessage("Invalid group in read group messages request");
            return response;
        }
        
        List<Model> groupMessages = dBBroker.readGroupMessages(group.getId());
        if(groupMessages.isEmpty()) {
            response.setMessage("No messages.");
            return response;
        }
        response.setSuccess(true);
        response.setMessage(format("Message count: %d", response.getResultList().size()));
        
        return response;
    }
    
}
