/*
 * To change this template, choose Tools | Templates
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
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ycaro_2
 */
public class ProgramCliente {
        
        private DatagramSocket clienteSocket; // socket para conectar com servidor
        private String endereco;
	private int porta;

public ProgramCliente(String endereco, int porta){
		this.endereco = endereco;
		this.porta = porta;
                
	}//fim do construtor
public void criarConexao() throws UnknownHostException, IOException{         

		//Cria Socket para fazer a conexão com o servidor
		clienteSocket = new DatagramSocket();

	}

public Pacote enviarMsn(Pacote mensagem) throws IOException, ClassNotFoundException{
		
                ByteArrayOutputStream byteoutput = new ByteArrayOutputStream();
                ObjectOutputStream output = new ObjectOutputStream(byteoutput);
                
                output.writeObject(mensagem);
		output.flush();
                output.close();
                
                byte[] data = byteoutput.toByteArray();
                
                // create sendPacket
                DatagramPacket sendPacket = new DatagramPacket( data, data.length, InetAddress.getByName(endereco), porta );
                
                clienteSocket.send(sendPacket);
                  // Aguardando servidor responder:
		Pacote pacote = (Pacote) waitForPackets(); // Ler a mensagem do servidor		
		return pacote;      
}

public Pacote waitForPackets() throws ClassNotFoundException, IOException
   {
            byte data[] = new byte[ 100 ]; // set up packet
            
            DatagramPacket receivePacket = new DatagramPacket( data, data.length );
            
            clienteSocket.receive( receivePacket );
            
            
            ByteArrayInputStream byteinput = new ByteArrayInputStream (receivePacket.getData());  
            ObjectInputStream input = new ObjectInputStream (byteinput);  
            Pacote pacote = (Pacote) input.readObject ();  
            input.close();  
            
   return pacote;  
   }

public void executaClient() throws IOException, ClassNotFoundException, SocketException{
		criarConexao();		
	}

    public DatagramSocket getClienteSocket() {
        return clienteSocket;
    }











































}
