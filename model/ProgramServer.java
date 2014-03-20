/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import trunk.view.TelaServer;

/**
 *
 * @author Ycaro_2
 */
public class ProgramServer {
    
private DatagramSocket Serversocket;
private int porta;
private TelaServer telaServer;


public ProgramServer(int porta){
                telaServer = new TelaServer();
		telaServer.setVisible(true);
		this.porta = porta;
}

public void ExecutarServidor() throws ClassNotFoundException{
        
    try{
        Serversocket = new DatagramSocket( porta );
        telaServer.exibirMessagem("Servidor Executando com Sucesso\n");
        
        } catch (IOException ex) {
			Logger.getLogger(ProgramServer.class.getName()).log(Level.SEVERE, null, ex);
		}
    
        while (true) {
			telaServer.exibirMessagem("Servidor esperando pacotes...\n");
			try {
				waitForPackets();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}


public Pacote waitForPackets() throws ClassNotFoundException, IOException
   {
            byte data[] = new byte[ 100 ]; // set up packet
            
            DatagramPacket receivePacket = new DatagramPacket( data, data.length );
            
            Serversocket.receive( receivePacket );
            
            
            ByteArrayInputStream byteinput = new ByteArrayInputStream (receivePacket.getData());  
            ObjectInputStream input = new ObjectInputStream (byteinput);  
            Pacote pacote = (Pacote) input.readObject ();  
            input.close();  
            
   return pacote;  
   }

private void sendPacketToClient( DatagramPacket receivePacket ) 
      throws IOException
   {
      displayMessage( "\n\nEcho data to client..." );

      // create packet to send
      DatagramPacket sendPacket = new DatagramPacket( 
         receivePacket.getData(), receivePacket.getLength(), 
         receivePacket.getAddress(), receivePacket.getPort() );

      socket.send( sendPacket ); // send packet to client
      displayMessage( "Packet sent\n" );
   } // end method sendPacketToClient

}
