/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import trunk.control.ChatControlador;

import trunk.view.TelaServer;

/**
 *
 * @author Karine
 */
public class ChatS implements Runnable{

    public ServerSocket servidor;
    public Socket conexao;
    public ChatControlador control;
    private int porta;
    
    //   Cliente novo;
    public ChatS(int porta, ChatControlador control) {
        this.control=control;
        this.porta = porta;    
 }
    
 public void run() { // Essa thread é necessária, pois cada cliente é um servidor
    try {
              ExecutarServidor();
        } catch (ClassNotFoundException ex) {
            System.out.println("Não executou o servidor");
        }
 }
    public void ExecutarServidor() throws ClassNotFoundException {

        try {
            servidor = new ServerSocket(porta);
            
        } catch (IOException ex) {
            Logger.getLogger(ChatS.class.getName()).log(Level.SEVERE, null, ex);
        }


        while (true) {
             try {
                esperaConexao();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void esperaConexao() throws IOException {

        conexao = servidor.accept();
        conexao.getInetAddress().getHostName();
        
        
        //Cria uma nova Thread para tratar esse conexão
        TrataChat thr = new TrataChat(conexao, control);        
        Thread t = new Thread(thr);
        t.start();
    }
}
