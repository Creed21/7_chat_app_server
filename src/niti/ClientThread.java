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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import requestResponse.Response;
import requestResponse.Request;
import requestResponse.LoginRequest;
import model.Message;
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

    public ClientThread(Socket s, ServerForm serverForm) throws IOException {
        this.socket = s;
        dbb = DBBroker.getInstance();
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        
        this.serverForm = serverForm;
        this.userService = new UserService();
        this.messageService = new MessageService();
    }

    @Override
    public void run() {
        boolean communicateWithClient = true;
        while (communicateWithClient) {
            Request request = readRequest();
            System.out.println("request: " + request);
            Response response = new Response();
            switch (request.getOperation()) {
                case LOGIN: 
                    LoginRequest login = (LoginRequest) request.getParametar();
                    user = dbb.login(login.getUsername(), login.getPassword());
                    if(user == null) {
                        response.setMessage("Wrong username or password!");
                    } else {
                        response.setMessage("Dobrodosli " + user.getUsername());
                        response.setSuccess(true);
                        response.setResult(user);
                        // potrebno je da sacuvamo korisnika kao ulogovanog usera i da ga prikazemo na serverskoj formi
                        Controller.getInstance().addLoggedUser(user);
                        // add user to logged ussers list on server form
                        serverForm.refreshLoggedUsers();
                    }
                    
                    break;
                    
                case SEND_MESSAGE:
                    Message sendMessage = (Message) request.getParametar();
                    response = messageService.sendMessage(sendMessage);
                    break;
                    
                case READ_CHAT_MESSAGES:
                    User usetTo = (User) request.getParametar();
                    response = messageService.readMessages(user, usetTo);
                    break;
                    
                case GET_ALL_USERS:
                    response.setSuccess(true);
                    response.setResultList(userService.getAllUsers());
                    break;
                    
                case EXIT:
                    communicateWithClient = false;
                    System.out.println("Klijent "+user.getUsername() +" se diskonektovao");
                    break;
                    
                default:
                    response.setSuccess(false);
                    response.setMessage("invalid operation request");
                    break;
            }
            sendResponse(response);
        }
    }

    private Request readRequest() {
        try {
            return (Request) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("KLIJENT SE ODVEZAO (UGASIO/LA SI KLIJENTSKU APLIKACIJU),"
                    + " ZATO SE DESIO OVAJ EXCEPTION, NE BRINI SE NISTA! ");
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            
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

}
