/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model.chat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import trunk.control.ChatControlador;
import trunk.model.Jogador;
import trunk.model.Pacote;
import trunk.model.TipoPacote;

/**
 *
 * @author Karine
 */
//Classe responsável por tratar todos os pacotes
public class TrataChat implements Runnable {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket conexao;
	private Jogador usuario;
        private ChatControlador controlChat;
        
    private int flag = 2;
	public TrataChat(Socket cliente, ChatControlador control) {
		super();
		this.conexao = cliente;
                this.controlChat=control;
	}

	//TODO Encerrar a conexão
	public void run() {

		try {
			System.out.println("Entro no método run da thread");

			obtemStreams();//Obtem streams para envio e recebimento de pacotes
			try {

				processaConexao(); //Chama método que processa o pacote

			} catch (Exception ex) {
				System.out.println("Problemas ao processar a conexao");
				Logger.getLogger(TrataChat.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (IOException ex) {
			Logger.getLogger(TrataChat.class.getName()).log(Level.SEVERE, null, ex);
		}


	}

	public void obtemStreams() throws IOException {

		//configura o fluxo de saída para objetos
		output = new ObjectOutputStream(conexao.getOutputStream());
		//output.flush(); 
		// configura o fluxo de entrada para objetos
		input = new ObjectInputStream(conexao.getInputStream());

		//ExibirMessagem("Streams de dados obstidos com Sucesso para o servidor!/n");
	}

	public void processaConexao() throws IOException {

		Pacote pacoteRecebido;

		try {
			while (!conexao.isClosed()) {// Enquanto a conexão não estiver fechada
				pacoteRecebido = new Pacote();
                                pacoteRecebido = (Pacote) input.readObject(); // Ler o pacote recebido do cliente
				//--------------AQUI COMEÇA UMA CASCATA DE IFs PARA SABER O TIPO DO PACOTE------------------------------
                                System.out.println(pacoteRecebido.getConteudo());
				//Verificando o tipo de Requisição:
				if(pacoteRecebido.getTipo() == TipoPacote.CARREGAR_USUARIO_CHAT){

					usuario = (Jogador) pacoteRecebido.getConteudo();

					Pacote pacoteConfirmacao;
					pacoteConfirmacao = new Pacote(TipoPacote.USUARIO_CHAT_CARREGADO, "COM SUCESSO");
					enviarMsn(pacoteConfirmacao);//Envia confirmação para o cliente                   

				} else if (pacoteRecebido.getTipo() == TipoPacote.ENVIAR_CHAT_MESSAGEM) {                    
                                        
                                        HashMap<String,Object> message = new HashMap<String, Object>();
					message = (HashMap<String, Object>) pacoteRecebido.getConteudo();
                                        
                                        for (int i = 0; i < controlChat.aplicacao1.participantes.size(); i++) {
                                        if(controlChat.aplicacao1.participantes.get(i).getLogin().equals(usuario.getLogin())){
                                        flag = 1; 
                                        }
                                        }
                                        if(flag==1){
                                            
                                            controlChat.chatWindow1.setMessageArea(usuario.getLogin(), message);
                                            
                                        }else {
                                            
                                            controlChat.chatWindow2.setMessageArea(usuario.getLogin(), message);
                                        }
					

					Pacote pacoteConfirmacao;
					pacoteConfirmacao = new Pacote(TipoPacote.MESSAGEM_RECEBIDA, "COM SUCESSO");
					enviarMsn(pacoteConfirmacao);//Envia confirmação para o cliente                    
				} else if(pacoteRecebido.getTipo() == TipoPacote.CONVITE_CHAT){
                                    
                                    //Tratar aqui sala a propria pessoa:
                                    
                                    int confirmacao=1; // confirmacao == 1== NÃO  e confirmacao ==0 == SIM
                                    
                                    if(controlChat.getMinhasSalasChat() < 2){
                                    
                                      confirmacao = JOptionPane.showConfirmDialog(null, "Confirme Presença no Chat");
                                      
                                      
                                    } else{
                                    
                                        confirmacao = 1;
                                    }
                                    if(confirmacao==0){
                                          
                                            Pacote pacoteConfirmacao;
                                            pacoteConfirmacao = new Pacote(TipoPacote.CONVITE_ACEITO, "SUCESSO");
                                            enviarMsn(pacoteConfirmacao);//Envia confirmação para o cliente
                                     }else{
                                         
                                            Pacote pacoteConfirmacao;
                                            pacoteConfirmacao = new Pacote(TipoPacote.CONVITE_RECUSADO, "RECUSADO");
                                            enviarMsn(pacoteConfirmacao);//Envia confirmação para o cliente
                                     }
                                
                                }else  if(pacoteRecebido.getTipo() == TipoPacote.ENCERRAR_CONEXAO_SAIR){
                                    
                                        encerrarConexao();
                                        
                                }else if (pacoteRecebido.getTipo() == TipoPacote.START_CHAT){
                                    
                                    ArrayList <Jogador> participantesChat = (ArrayList <Jogador>) pacoteRecebido.getConteudo();
                                    
                                    controlChat.startChat(participantesChat);
                                    controlChat.IncrementeMinhasSalasChat();//Quando o usuário for convidado incrementa a quantidade de suas salas
                                }else if(pacoteRecebido.getTipo() == TipoPacote.ENCERRAR_CHAT){
                                 for (int i = 0; i < controlChat.aplicacao1.participantes.size(); i++) {
                                        if(controlChat.aplicacao1.participantes.get(i).getLogin().equals(usuario.getLogin())){
                                        flag = 1; 
                                        }
                                        }
                                        if(flag==1){   
                                        JOptionPane.showMessageDialog(controlChat.chatWindow1, "Bate-papo encerrado pelo criador, "+usuario.getLogin());
                                        controlChat.chatWindow1.dispose();
                                        controlChat.aplicacao1.fecharConexao();
                                        controlChat.DecrementeMinhasSalasChat();
                                        encerrarConexao();                                        
                                }else {
                                        JOptionPane.showMessageDialog(controlChat.chatWindow2, "Bate-papo encerrado pelo criador, "+usuario.getLogin());
                                        controlChat.chatWindow2.dispose();
                                        controlChat.aplicacao2.fecharConexao();
                                        controlChat.DecrementeMinhasSalasChat();
                                        encerrarConexao();
                                 }
                              }
			}
		} catch (IOException exc) {


			System.out.println("ANTES: " + usuario.getStatus());
			System.out.println(exc.toString() + "\n" + "A conexão com o cliente foi encerrada- Exceção");


		} catch (ClassNotFoundException ex) {
			Logger.getLogger(TrataChat.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(ex.toString() + "\n" + "Processamento de com Problemas");
		}
	}

	//Envia qualquer pacote ao Cliente
	public void enviarMsn(Pacote mensagem) throws IOException {
		//Envia o Objeto 
		output.writeObject(mensagem);
		output.flush();
	}

	public void enviarEmail(Pacote mensagem) throws IOException {
	}

	public void encerrarConexao() throws IOException {
		output.close(); //fecha fluxo de saída
		input.close(); //fecha fluxo de entrada
		conexao.close(); //fecha conexão
	}
}
