/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ycaro_2
 */
public class TrataPacote implements Runnable{

    @Override
    public void run() {
            try {
                processaPacote();
            } catch (IOException ex) {
                System.out.println("Problemas ao processar o pacote");
                Logger.getLogger(TrataPacote.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void processaPacote() throws IOException {
    
    
    }
    private void sendPacketToClient( DatagramPacket receivePacket ) 
      throws IOException
   {
      

      // create packet to send
      DatagramPacket sendPacket = new DatagramPacket( 
         receivePacket.getData(), receivePacket.getLength(), 
         receivePacket.getAddress(), receivePacket.getPort() );

      socket.send( sendPacket ); // send packet to client
      displayMessage( "Packet sent\n" );
   } // end method sendPacketToClient

}
}
