/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import start_form.ServerForm;

/**
 *
 * @author USER
 */
public class ServerStart extends Thread {

    private int port;
    private volatile boolean run;
    private ServerSocket serverSocket;
    private ServerForm serverForm;
    
    public ServerStart() {
    }

    public ServerStart(int port) {
        this.port = port;
    }
    
    public ServerStart(int port, ServerForm serverForm) {
        this.port = port;
        this.serverForm = serverForm;
    }

    @Override
    public void run() {
        try {
            run = true;
            serverSocket = new ServerSocket(port);
            while (run) {
                System.out.println("Cekanje klijenta...");
                Socket s = serverSocket.accept();
                System.out.println("Klijent se povezao!");
                ClientThread nit = new ClientThread(s, serverForm);
                nit.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopServer() {
        this.run = false;
        
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerStart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("server stopped");
    }

}
