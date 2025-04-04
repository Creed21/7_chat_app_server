/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import baza.DBBroker;
import controller.Controller;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Group;
import model.User;
import requestResponse.Response;
import requestResponse.Request;
import requestResponse.LoginRequest;
import model.Message;
import requestResponse.UserGroupRequest;
import service.MessageService;
import service.UserService;
import start_form.ServerForm;

/**
 *
 * @author USER
 */
public class ClientThread extends Thread {

    private Socket socket;
    private DBBroker dbb;
    private User user;
    private ServerForm serverForm;
    private UserService userService;
    private MessageService messageService;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
//    private SendingThread sendingThread;

    public ClientThread(Socket s, ServerForm serverForm) throws IOException {
        this.socket = s;
        dbb = DBBroker.getInstance();
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        
        this.serverForm = serverForm;
        this.userService = new UserService();
        this.messageService = new MessageService();
//        this.sendingThread = new SendingThread(socket);
    }

    @Override
    public void run() {
        try{
        boolean communicateWithClient = true;
        while (communicateWithClient) {
            Request request = readRequest();
            System.out.println("request: " + request);
            Response response = new Response();
            switch (request.getOperation()) {
                // users
                case LOGIN: 
                    LoginRequest login = (LoginRequest) request.getParametar();
                    response = userService.logIn(login.getUsername(), login.getPassword());
                    User loggedUser = (User) response.getResult();
                    if(loggedUser != null) {
                        // potrebno je da sacuvamo korisnika kao ulogovanog usera i da ga prikazemo na serverskoj formi
                        user = loggedUser;
                        Controller.getInstance().addLoggedUser(loggedUser);
                        // add user to logged ussers list on server form
                        serverForm.refreshLoggedUsers();
                    }
                    break;
                    
                case GET_ALL_USERS:                    
                    response = userService.getAllUsers();
                    break;
                    
                case ADD_USER_GROUP:
                    UserGroupRequest userGroupRequest = (UserGroupRequest) request.getParametar();
                    response = userService.addUserInGroup(userGroupRequest);
                    break;
                    
                // direct messages
                case SEND_MESSAGE:
                    Message sendMessage = (Message) request.getParametar();
                    response = messageService.sendMessage(sendMessage);
                    break;
                
                case READ_CHAT_MESSAGES:
                    User usetTo = (User) request.getParametar();
                    response = messageService.readMessages(user, usetTo);
                    break;
                
                // group messages
                case SEND_GROUP_MESSAGE:
                    Message message = (Message) request.getParametar();
                    response = messageService.sendMessage(message);
                    break;
                    
                case READ_GROUP_MESSAGE:
                    Group group = (Group) request.getParametar();
                    response = messageService.readGroupMessages(group);
                    break;
                    
                // exit
                case EXIT:
                    communicateWithClient = false;
                    System.out.println("Client "+user.getUsername() +" disconnected!!!");
                    break;
                    
                default:
                    response.setSuccess(false);
                    response.setMessage("invalid operation request");
                    break;
            }
            sendResponse(response);
        }
        } catch (Exception e) {
            Controller.getInstance().kickUser(user);
        }
    }

    private Request readRequest() {
        try {
            return (Request) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Client disconnected!");
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            
            if(!socket.isClosed()) {
                sendResponse(
                    new Response("You have been kicked!", false, null, null)
                );
            }
            
            Controller.getInstance().kickUser(user);
            serverForm.refreshLoggedUsers();
        }
        return null;
    }

    private void sendResponse(Response so) {
        try {
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getUser() {
        return user;
    }
    
    

}
