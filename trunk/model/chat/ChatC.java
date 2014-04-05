/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model.chat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import trunk.model.Jogador;
import trunk.model.Pacote;
import trunk.model.TipoPacote;

/**
 *
 * @author Karine
 */
public class ChatC {

    private ArrayList<ObjectOutputStream> output = new ArrayList<ObjectOutputStream>();//cria fluxo de entrada
    private ArrayList<ObjectInputStream> input = new ArrayList<ObjectInputStream>();//cria fluxo de saída
    private ArrayList<Socket> conexaoCliente = new ArrayList<Socket>();//Cria Socket dos Clientes
    public ArrayList<Jogador> participantes;
    public Semaphore semaforo;

    public ChatC(ArrayList<Jogador> participantes) {// Cria a janela

        this.participantes = new ArrayList<Jogador>(participantes);
        this.semaforo = new Semaphore(1);

    }//fim do construtor 

    public void criarConexoes() throws UnknownHostException, IOException {

        //Cria Socket para fazer a conexões com os participantes
        for (int i = 0; i < participantes.size(); i++) {
        	System.out.println("conectando a: "+participantes.get(i).getInetAddress()+":"+participantes.get(i).getPorta());
            conexaoCliente.add(i, new Socket(participantes.get(i).getInetAddress(), participantes.get(i).getPorta()));

        }
    }

    public void obterStreams() throws IOException {

        for (int i = 0; i < participantes.size(); i++) {
            //Configura o fluxo de saída para objetos
            output.add(i, new ObjectOutputStream(conexaoCliente.get(i).getOutputStream()));

            //Configura o fluxo de entrada para objetos
            input.add(i, new ObjectInputStream(conexaoCliente.get(i).getInputStream()));

        }
    }

    public void fecharConexao() throws IOException {
        //fecha os fluxos e os sockets
        for (int i = 0; i < participantes.size(); i++) {
            output.get(i).close();
            input.get(i).close();
            conexaoCliente.get(i).close();
        }
    }

    public Pacote enviarMsn(Pacote mensagem) throws IOException, ClassNotFoundException {
        try {
            //Envia o Objeto ao Servidor
            semaforo.acquire();//
        } catch (InterruptedException ex) {
            Logger.getLogger(ChatC.class.getName()).log(Level.SEVERE, null, ex);
        }
        Pacote pacote =null;
        
        if(participantes.isEmpty()){
        pacote = new Pacote(TipoPacote.USUARIOS_OFFLINES, "Usuarios offs");
        }
        
        for (int i = 0; i < participantes.size(); i++) {
            try {
                output.get(i).writeObject(mensagem);
                output.get(i).flush();

                // Aguardando servidor responder:
                pacote = (Pacote) input.get(i).readObject(); // Ler a mensagem do servidor
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Participante "+participantes.get(i).getLogin()+" saiu do chat");
                participantes.remove(i);
                output.remove(i);
                input.remove(i);
                conexaoCliente.remove(i);                
                continue;
            }
        }

        semaforo.release();//Libera o recurso

        return pacote;
    }

    public void executaClient() throws IOException, ClassNotFoundException, SocketException {
        criarConexoes();
        obterStreams();
    }

    public ArrayList<Jogador> getParticipantes() {
        return participantes;
    }
}// Fim da Classe