/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trunk.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ycaro_2
 */
class TrataPartida implements Runnable{
private ArrayList jogadores;

    TrataPartida(ArrayList jogadores) {
        this.jogadores = jogadores;
    }

    @Override
    public void run() {
       try {
                processaPacotePartida();
                
            } catch (IOException ex) {
                System.out.println("Problemas ao processar o pacote da Partida");
                Logger.getLogger(TrataPacote.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    private void processaPacotePartida() throws IOException{
        
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
      Serversocket.send( sendPacket ); // send packet to client
      
   } // end method sendPacketToClient

}
