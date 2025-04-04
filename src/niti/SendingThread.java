/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.Socket;
import requestResponse.Response;

/**
 *
 * @author Korisnik
 */
public class SendingThread extends Thread {
    private Socket socket;
    private ObjectOutputStream oos;

    public SendingThread(Socket socket) throws IOException {
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
    }
    
    private void sendResponse(Response so) {
        try {
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(SendingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        boolean run = true;
        while(run) {
//            sendResponse(so);
        }
    }
    
    
    
}
