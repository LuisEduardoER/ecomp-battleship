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

import javax.swing.JOptionPane;

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
        
       ArrayList temp = (ArrayList)pacoteRecebido.getConteudo();
       Jogador jog = (Jogador)temp.get(0);
       int pLinha = (int)temp.get(1);
       int pColuna = (int)temp.get(2);
       
       //TERMINANDO
      Jogador jog1 = (Jogador)jogadores.get(0);
      Jogador jog2 =(Jogador)jogadores.get(1);
            
       //Se o jogador que deu o tiro for igual ao jogador 1, o tabuleiro a ser modificado sera o tab2
       	if(jog1 == jog){
       		
       	Tabuleiro tabTemp = PartidaS.getTab2();
       	tabTemp.darTiro(pLinha,pColuna);
       	PartidaS.setTab2(tabTemp);
       	
       		
       		
       		
       	}else{//Se o jogador que deu o tiro n�o  for igual ao jogador 1,ou seja
       		//ele � o jogador2, o tabuleiro a ser modificado sera o tab1
       		
       		
       		
       		
       	}
       		
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
