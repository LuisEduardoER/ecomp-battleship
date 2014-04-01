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
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author Ycaro_2
 */
class PartidaS {
    
    private ArrayList jogadores;
    private DatagramSocket PartidaServersocket;
    private static Tabuleiro tab1;
    private static Tabuleiro tab2;

    
    public static Tabuleiro getTab1() {
		return tab1;
	}


	public static void setTab1(Tabuleiro tab1) {
		PartidaS.tab1 = tab1;
	}


	public static Tabuleiro getTab2() {
		return tab2;
	}


	public static void setTab2(Tabuleiro tab2) {
		PartidaS.tab2 = tab2;
	}


	public PartidaS(ArrayList jogadores){
    this.jogadores = jogadores;
    }
    
    
    public void ExecutarServidor() throws ClassNotFoundException{
        
    try{
        PartidaServersocket = new DatagramSocket( 46147 );
        
        tab1.inicializaEmbarcacao();
        tab1.Sorteio();
        
        tab2.inicializaEmbarcacao();
        tab2.Sorteio();
        
        AvisarJogadores();
        } catch (IOException ex) {
			Logger.getLogger(ProgramServer.class.getName()).log(Level.SEVERE, null, ex);
		}

    while (true) {
			
			try {
				waitForPackets();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    
    }
    
    
public void waitForPackets() throws ClassNotFoundException, IOException
   {
            byte data[] = new byte[ 1000 ]; // set up packet
            
            DatagramPacket receivePacket = new DatagramPacket( data, data.length );            
            PartidaServersocket.receive( receivePacket );
            
            
            TrataPartida thr = new TrataPartida(ProgramServer.getJogadores(), PartidaServersocket);                                            
            Thread t = new Thread(thr);
            t.start(); 
   }

public void AvisarJogadores() throws IOException{
        for(int i=0;i<jogadores.size();i++){
        Pacote pacoteConfirmacao = new Pacote(TipoPacote.COMECA_PARTIDA, "COMECA_PARTIDA");
        Jogador jogador = (Jogador) jogadores.get(i);
        sendPacketToClient(pacoteConfirmacao,jogador);
        }

}

private void sendPacketToClient( Pacote pacoteConfirmacao, Jogador jogador ) throws IOException {      
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
      jogador.getEndereco(), jogador.getPorta() );
      PartidaServersocket.send( sendPacket ); // send packet to client
      
   }











}
