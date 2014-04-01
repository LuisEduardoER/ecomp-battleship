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
import java.util.ArrayList;
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
private static Integer num_Partidas = new Integer(1);
private static Integer num_Jogadores = new Integer(2);
private static ArrayList Jogadores =  new ArrayList() ;

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
			for(Usuario jogador:bancoDeJogadores){
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

    public static Integer getNumPartida() {
        return num_Partidas;
    }

    public static void setNumPartida(Integer Num_Partidas) {
        ProgramServer.num_Partidas = Num_Partidas;
    }

    public static Integer getNum_Jogadores() {
        return num_Jogadores;
    }

    public static void setNum_Jogadores(Integer num_Jogadores) {
        ProgramServer.num_Jogadores = num_Jogadores;
    }
    
    public static ArrayList getJogadores() {
		return Jogadores;
	}

    public static void addJogadores(Jogador jogador) {
		ProgramServer.Jogadores.add(jogador);
	}
        public static void removeJogadores(Jogador jogador) {
		ProgramServer.Jogadores.remove(jogador);
	}

}
