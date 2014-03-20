/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
private static BancoDeJogadores bancoDeJogadores = new BancoDeJogadores();
private File arquivoJogadores;
private ObjectInputStream jogadoresInput;

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
        arquivoJogadores = new File("jogadores.bin");
		try {
			jogadoresInput = new ObjectInputStream(new FileInputStream(arquivoJogadores));
			bancoDeJogadores = (BancoDeJogadores) jogadoresInput.readObject();
			jogadoresInput.close();
			telaServer.exibirMessagem("Lendo jogadores cadastrados:\n");
			telaServer.exibirMessagem("   "+"Login:SENHA\n");
			for(Jogador jogador:bancoDeJogadores){
				telaServer.exibirMessagem("   "+jogador.getLogin()+":"+jogador.getSenha()+"\n");
			}
			telaServer.exibirMessagem("\n");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
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


public void waitForPackets() throws ClassNotFoundException, IOException
   {
            byte data[] = new byte[ 1000 ]; // set up packet
            
            DatagramPacket receivePacket = new DatagramPacket( data, data.length );            
            Serversocket.receive( receivePacket );
            
            
            TrataPacote thr = new TrataPacote(Serversocket, receivePacket);
            telaServer.exibirMessagem(receivePacket.getData().getClass()+"\n\n");
            Thread t = new Thread(thr);
            t.start();  
   }

    public static BancoDeJogadores getBancoDeJogadores() {
        return bancoDeJogadores;
    }

    public static void setBancoDeJogadores(BancoDeJogadores bancoDeJogadores) {
        ProgramServer.bancoDeJogadores = bancoDeJogadores;
    }


}
