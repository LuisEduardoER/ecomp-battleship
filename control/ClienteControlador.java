/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;
import trunk.model.BancoDeJogadores;
import trunk.model.Jogador;
import trunk.model.Pacote;
import trunk.model.ProgramCliente;
import trunk.model.TipoPacote;
import trunk.view.LoginWindow;
import trunk.view.TelaCadastro;

/**
 *
 * @author Ycaro_2
 */
public class ClienteControlador implements ActionListener, MouseInputListener, KeyListener{
    
    private LoginWindow loginJanela;
    private TelaCadastro telaCadastro;
    public ProgramCliente aplicacao;
    private BancoDeJogadores jogadores;
    public Jogador jogador;
    public ClienteControlador(){
        
     loginJanela = new LoginWindow(this);
    }    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Eventos da Janela de Login
        if (loginJanela != null && e.getSource() == loginJanela.getCancelButton()) {
			loginJanela.dispose();

		} else if (loginJanela != null && e.getSource() == loginJanela.getOkButton()) {

                            String login = loginJanela.getLoginCampo().getText();
                            String senha = loginJanela.getSenhaCampo().getText();
                            String servidor = loginJanela.getServidorCampo().getText();

                            servidor = servidor.equals("") ? "127.0.0.1" : servidor;

                            if (login.equals("") || senha.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Todos os campos precisam ser preenchidos");
                            }else{
                           //VAMOS VERIFICAR SE O CLIENTE ESTÃ� CADASTRADO NO SISTEMA PRIMEIRO
				jogador = new Jogador(login, senha);

				//Cria um novo pacote para ser enviado par ao servidor
				Pacote pacote = new Pacote(TipoPacote.LOGIN, jogador);
				
				aplicacao = new ProgramCliente(servidor, 26147);

				try {
					aplicacao.executaClient(); //Executa a aplicaÃ§Ã£o cliente 
					Pacote resposta = aplicacao.enviarMsn(pacote);//O mÃ©todo enviarMsn envia o pacote para o servidor e retorna uma resposta

					System.out.println(resposta.getTipo().toString());

					// Se o a confirmaÃ§Ã£o do servidor for positiva:
					if (resposta.getTipo() == TipoPacote.SUCESSO_LOGIN) {
						JOptionPane.showMessageDialog(null, "Login realizado com sucesso");

						HashMap<String, Object> aux = (HashMap<String, Object>) resposta.getConteudo();
						jogadores = (BancoDeJogadores) aux.get("jogadores");


						loginJanela.dispose();
						loginJanela.getLoginCampo().setText("");
						loginJanela.getSenhaCampo().setText("");
						loginJanela.getServidorCampo().setText("");
						                                      for (int i = 0; i < jogadores.size(); i++) {
                                                System.out.println(jogadores.getJogador(i).getLogin());
                                            }
                                                
						
                                                //CHAMAR TELA DO JOGO new viewEcompBattleship();
					}

					// Se o usuÃ¡rio nÃ£o estiver cadastrado:
					if (resposta.getTipo() == TipoPacote.NAO_CADASTRADO) {//Se o cliente não estiver cadastrado
						JOptionPane.showMessageDialog(null, "Você precisa se cadastrar primeiro");
                                        }

				} catch (SocketException ex) {
					JOptionPane.showMessageDialog(null, "Não foi possível estabelecer uma conexão com o servidor informado!", "", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					System.out.println("Problema com a requisição de Login -- Classe ClienteControlador");
					Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
				}                            
                        }
                            
                            
                }else //Eventos da Tela de Cadastro
                    if (telaCadastro != null && e.getSource() == telaCadastro.getCancelButton()) {
					telaCadastro.dispose();
					loginJanela.setVisible(true);
				} //Se o evento for do botão CADASTRAR de TelaCadastro:
				else if (telaCadastro != null && e.getSource() == telaCadastro.getCadastroButton()) {

					//Captura dos dados da tela de Cadastro:
					String nome = telaCadastro.getCampoNome().getText();
					String senha = telaCadastro.getCampoSenha().getText();
					String confirmaSenha = telaCadastro.getCampoConfirmarSenha().getText();

					//Se algum campo estiver vazio:
					if (senha.equals("") || nome.equals("")) {
						JOptionPane.showMessageDialog(null, "Todos os campos precisam ser preenchidos");
					} else {
						//Verifica se a senha é igual a senha confirmada:
						if (senha.equals(confirmaSenha) && !senha.equals("")) {
                                                    //CADASTRA, MONTA PACOTE E ENVIA!
                                                    //Cria novo cliente:
							Jogador novoJogador = new Jogador(nome, senha);

							//Cria um novo pacote:
							Pacote pacote = new Pacote(TipoPacote.CADASTRO, novoJogador);
                                                
                                                    aplicacao = new ProgramCliente("127.0.0.1", 26147);    
                                                    try {
                                                        
                                                    aplicacao.executaClient(); //Executa a aplicação cliente
                                                    Pacote resposta = aplicacao.enviarMsn(pacote);
                                                    aplicacao.fecharConexao();
                                                    if (resposta.getTipo() == TipoPacote.SUCESSO_CADASTRO) { // Se o a confirmaÃ§Ã£o do servidor for positiva:
									JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
									telaCadastro.dispose();
									loginJanela.setVisible(true); // Volta a tela de login
								}
                                                } catch (IOException ex) {
                                                    Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (ClassNotFoundException ex) {
                                                    Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                    System.out.println("login: "+nome+" senha: "+senha);
						
                                                } else {
							JOptionPane.showMessageDialog(null, "Digite novamente a senha e confirme");

						}

					}

				}
}
    @Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == loginJanela.getCadastro()) {

			telaCadastro = new TelaCadastro(this);
			telaCadastro.setVisible(true);
			loginJanela.setVisible(false);
		}
	}

    @Override
    public void mousePressed(MouseEvent me) {        
    }

    @Override
    public void mouseReleased(MouseEvent me) {        
    }

    @Override
    public void mouseEntered(MouseEvent me) {        
    }

    @Override
    public void mouseExited(MouseEvent me) {       
    }

    @Override
    public void mouseDragged(MouseEvent me) {        
    }

    @Override
    public void mouseMoved(MouseEvent me) {        
    }

    @Override
    public void keyTyped(KeyEvent ke) {       
    }

    @Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			loginJanela.getOkButton().doClick();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			loginJanela.getCancelButton().doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
