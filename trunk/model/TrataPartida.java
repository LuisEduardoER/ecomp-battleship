/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trunk.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ycaro_2
 */
class TrataPartida implements Runnable{
private ArrayList jogadores;
private DatagramSocket PartidaServersocket;
    TrataPartida(ArrayList jogadores, DatagramSocket PartidaServersocket) {
        this.jogadores = jogadores;
        this.PartidaServersocket = PartidaServersocket;
    }

    @Override
    public void run() {
       try {
             while(true){
                waitForPackets();
             }
                        
                
            } catch (IOException ex) {
                System.out.println("Problemas ao processar o pacote da Partida");
                Logger.getLogger(TrataPacote.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                   Logger.getLogger(TrataPartida.class.getName()).log(Level.SEVERE, null, ex);
               }
    }

    private void processaPacotePartida(DatagramPacket receivePacket) throws IOException{
    Pacote pacoteRecebido;
            try {  
                
                ByteArrayInputStream byteinput = new ByteArrayInputStream (receivePacket.getData());  
                ObjectInputStream input = new ObjectInputStream (byteinput);  
                pacoteRecebido = (Pacote) input.readObject ();
                input.close(); 
        
        
        if(pacoteRecebido.getTipo() == TipoPacote.TIRO){
        
        
        }    
        
            }  catch (ClassNotFoundException ex) {
			Logger.getLogger(TrataPacote.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(ex.toString() + "\n" + "Processamento de com Problemas");
		}
    }
    
    private void sendPacketToClient( Pacote pacoteConfirmacao, DatagramPacket receivePacket ) throws IOException {      
        //Tranformar o Pacote em array de Bytes
        ByteArrayOutputStream byteoutput = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(byteoutput);
        output.writeObject(pacoteConfirmacao);
	output.flush();
        output.close();
        byte[] data = byteoutput.toByteArray();
        //final da transformacao
        
      // create packet to send
      DatagramPacket sendPacket = new DatagramPacket( data, data.length, 
      receivePacket.getAddress(), receivePacket.getPort() );
      PartidaServersocket.send( sendPacket ); // send packet to client
      
   } // end method sendPacketToClient

public void waitForPackets() throws ClassNotFoundException, IOException
   {
            byte data[] = new byte[ 1000 ]; // set up packet
            
            DatagramPacket receivePacket = new DatagramPacket( data, data.length );            
            PartidaServersocket.receive( receivePacket );
            processaPacotePartida(receivePacket);
            
              
   }


}
