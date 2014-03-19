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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;
import trunk.view.LoginWindow;
import trunk.view.TelaCadastro;

/**
 *
 * @author Ycaro_2
 */
public class ClienteControlador implements ActionListener, MouseInputListener, KeyListener{
    
    private LoginWindow loginJanela;
    private TelaCadastro telaCadastro;
    
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
                            System.out.println("login: "+login+" senha: "+senha);

                            loginJanela.dispose();
                            loginJanela.getLoginCampo().setText("");
                            loginJanela.getSenhaCampo().setText("");
                            loginJanela.getServidorCampo().setText("");
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
