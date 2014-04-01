/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ycaro_2
 */
public class TrataPacote implements Runnable{
    
    private DatagramSocket Serversocket;
    private DatagramPacket receivePacket;
    private File arquivoJogadores;
    private ObjectOutputStream jogadoresOutput;
    
    
    
    TrataPacote(DatagramSocket Serversocket, DatagramPacket receivePacket) {
        this.Serversocket = Serversocket;
        this.receivePacket = receivePacket;
    }

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
            Pacote pacoteRecebido;
            try {  
                
                ByteArrayInputStream byteinput = new ByteArrayInputStream (receivePacket.getData());  
                ObjectInputStream input = new ObjectInputStream (byteinput);  
                pacoteRecebido = (Pacote) input.readObject ();
                input.close();   
             
             //--------------AQUI COMEÇA UMA CASCATA DE IFs PARA SABER O TIPO DO PACOTE------------------------------

				//Verificando o tipo de Requisição:
				//Se a Requisição for um CADASTRO:
				if (pacoteRecebido.getTipo() == TipoPacote.CADASTRO) {

					//é um cliente para ser inserido no banco de dados
					Usuario cadastro = (Usuario) pacoteRecebido.getConteudo();
					BancoDeJogadores bancoDeJogadores = new BancoDeJogadores(ProgramServer.getBancoDeJogadores());
					if (bancoDeJogadores.inserirJogador(cadastro)) {
						ProgramServer.setBancoDeJogadores(bancoDeJogadores);
						System.out.println("Jogador Cadastrado no Banco de Jogadores");
						arquivoJogadores = new File("jogadores.bin");
						jogadoresOutput = new ObjectOutputStream(new FileOutputStream(arquivoJogadores));
						jogadoresOutput.writeObject(bancoDeJogadores);
						jogadoresOutput.flush();
						Pacote pacoteConfirmacao = new Pacote(TipoPacote.SUCESSO_CADASTRO, "SUCESSO");
						sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente
					} else {
						System.out.println("Jogador não pôde ser cadastrado");
						Pacote pacoteConfirmacao = new Pacote(TipoPacote.FALHA_LOGIN, "FALHA");
						sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente
					}
				}//Se a Requisição for um LOGIN:
				else if (pacoteRecebido.getTipo() == TipoPacote.LOGIN) {

					Pacote pacoteConfirmacao;
					//é um cliente para ser buscado no banco de jogadores
					Usuario buscarCadastro = (Usuario) pacoteRecebido.getConteudo();
					BancoDeJogadores bancoDeJogadores = new BancoDeJogadores(ProgramServer.getBancoDeJogadores());
					if (bancoDeJogadores.autentica(buscarCadastro)) {
						ProgramServer.setBancoDeJogadores(bancoDeJogadores);
						HashMap<String, Object> conteudo = new HashMap<String, Object>();
						conteudo.put("jogadores", bancoDeJogadores);						
						pacoteConfirmacao = new Pacote(TipoPacote.SUCESSO_LOGIN, conteudo);
						sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente
						System.out.println("Busca realizada com Sucesso: " + buscarCadastro.getLogin());

					} else {//Se Cliente não existe no bancoDeJogadores:                           
						pacoteConfirmacao = new Pacote(TipoPacote.NAO_CADASTRADO, "SEM SUCESSO");
						sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente						
					}       
                                
                                } else if (pacoteRecebido.getTipo() == TipoPacote.PROCURAR_OPONENTE){
                                    int num_partida = ProgramServer.getNumPartida();
                                    int num_jogadores = ProgramServer.getNum_Jogadores();
                                    Pacote pacoteConfirmacao;
                                    if(num_partida==1){
                                        
                                        if(num_jogadores==2){
                                            
                                            Jogador j1 = (Jogador) pacoteRecebido.getConteudo();
                                            j1.setEndereco(receivePacket.getAddress());
                                            j1.setPorta(receivePacket.getPort());
                                            ProgramServer.addJogadores(j1);
                                            
                                            num_jogadores--;
                                            ProgramServer.setNum_Jogadores(num_jogadores);
                                            pacoteConfirmacao = new Pacote(TipoPacote.ESPERAR_OPONENTE, "COM SUCESSO");
                                            sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente	
                                        }else if(num_jogadores==1){
                                            
                                           Jogador j2 = (Jogador) pacoteRecebido.getConteudo();
                                            j2.setEndereco(receivePacket.getAddress());
                                            j2.setPorta(receivePacket.getPort());
                                            ProgramServer.addJogadores(j2);
                                            
                                            num_jogadores--;
                                            ProgramServer.setNum_Jogadores(num_jogadores);                                            
                                            num_partida--;
                                            ProgramServer.setNumPartida(num_partida);
                                            
                                            PartidaS servidorPartida = new PartidaS(ProgramServer.getJogadores());
                                            servidorPartida.ExecutarServidor();
                                            
                                            pacoteConfirmacao = new Pacote(TipoPacote.ESPERAR_OPONENTE, "COM SUCESSO");
                                            sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente 
                                        }else{
                                        //Se já existe partida sendo jogada no momento.                           
						pacoteConfirmacao = new Pacote(TipoPacote.EXISTE_PARTIDA, "SEM SUCESSO");
						sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente	
                                        }       
                                    }else{
                                        //Se já existe partida sendo jogada no momento.                           
						pacoteConfirmacao = new Pacote(TipoPacote.EXISTE_PARTIDA, "SEM SUCESSO");
						sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente	
                                    }
                                }else if(pacoteRecebido.getTipo() == TipoPacote.LIBERAR_PARTIDA){
                                    
                                    int num_partida = ProgramServer.getNumPartida();
                                    Pacote pacoteConfirmacao;
                                    
                                    if(num_partida==0){
                                        
                                        num_partida++;
                                        ProgramServer.setNumPartida(num_partida);
                                        pacoteConfirmacao = new Pacote(TipoPacote.PARTIDA_LIBERADA, "COM SUCESSO");
					sendPacketToClient(pacoteConfirmacao, receivePacket);//Envia confirmação para o cliente
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
      Serversocket.send( sendPacket ); // send packet to client
      
   } // end method sendPacketToClient

}

