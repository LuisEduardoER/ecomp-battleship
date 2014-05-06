package trunk.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MouseInputListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import trunk.model.Jogador;
import trunk.model.Pacote;
import trunk.model.TipoPacote;
import trunk.model.chat.ChatC;
import trunk.model.chat.ChatS;
import trunk.view.ChatWindow;


public class ChatControlador implements ActionListener, MouseInputListener, KeyListener, MenuListener, WindowListener {

    public ChatWindow chatWindow1;
    public ChatC aplicacao1;
    public ChatWindow chatWindow2;
    public ChatC aplicacao2;
    public ArrayList<Jogador> participantes;
    public Jogador usuario;    
    private int minhasSalas_Chat = 0;
    private ClienteControlador usuarioControl;
    private int flag = 0;

    public ChatControlador(Jogador jogador, int porta, ClienteControlador usuarioControl) {// Cada usuário que loga é um servidor
        this.usuario = usuario;
        this.usuarioControl = usuarioControl;
        //Aqui ocorre a execução do servidor como uma Tread
        ChatS likeServer = new ChatS(porta, this); //executa o servidor, basta passar por parâmetro a porta
        Thread t = new Thread(likeServer);
        t.start();
    }

    public void startChat(ArrayList<Jogador> participantes) throws IOException, ClassNotFoundException {
        this.participantes = participantes;
        if (chatWindow1 == null) {

            chatWindow1 = new ChatWindow(this);

            aplicacao1 = new ChatC(this.participantes);

            try {
                aplicacao1.executaClient();
            } catch (SocketException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível estabelecer uma conexão com o servidor informado! " + ex, "", JOptionPane.ERROR_MESSAGE);
            }

            Pacote pacote = new Pacote(TipoPacote.CARREGAR_USUARIO_CHAT, usuario);

            try {
                Pacote resposta = aplicacao1.enviarMsn(pacote);

            } catch (IOException ex) {
                Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            chatWindow2 = new ChatWindow(this);

            aplicacao2 = new ChatC(this.participantes);

            try {
                aplicacao2.executaClient();
            } catch (SocketException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível estabelecer uma conexão com o servidor informado! " + ex, "", JOptionPane.ERROR_MESSAGE);
            }

            Pacote pacote = new Pacote(TipoPacote.CARREGAR_USUARIO_CHAT, usuario);

            try {
                Pacote resposta = aplicacao2.enviarMsn(pacote);

            } catch (IOException ex) {
                Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Metodo para enviar aos participantes do chat uma menssagem

    public void enviarChatMessagem(HashMap<String, Object> message, ChatC aplicacao, ChatWindow chatWindow) {
        Pacote pacote = new Pacote(TipoPacote.ENVIAR_CHAT_MESSAGEM, message);
        try {
            Pacote resposta = aplicacao.enviarMsn(pacote);
            if (resposta.getTipo() == TipoPacote.MESSAGEM_RECEBIDA) {
                chatWindow.setMessageArea("Você " + usuario.getLogin(), message);
                chatWindow.clearEnterArea();
            } else if (resposta.getTipo() == TipoPacote.USUARIOS_OFFLINES) {
                JOptionPane.showMessageDialog(chatWindow, "Nenhum usuario está online");

            }

        } catch (IOException ex) {
            Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //KARINE
    public boolean liberar_Sala_Chat() {

        //SE a REQUISIÇÃO FOR PARA LIBERAÇÃO DE UMA SALA DE BATE-PAPO
        Pacote liberar = new Pacote(TipoPacote.LIBERAR_SALA_CHAT, usuario);

        try {
            Pacote resposta = usuarioControl.aplicacao.enviarMsn(liberar);
            if (resposta.getTipo() == TipoPacote.SALA_CHAT_LIBERADA) {
                System.out.println("A SALA DE CHAT FOI LIBERADA");
                return true;
            } else if (resposta.getTipo() == TipoPacote.SALA_CHAT_NAO_LIBERADA) {
                System.out.println("A SALA DE CHAT NAO FOI LIBERADA");
                return false;
            }
        } catch (Exception ex) {

            System.out.println("NÃO CONSEGUIU LIBERAR A SALA");
            Logger.getLogger(ClienteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("A SALA DE CHAT NAO FOI LIBERADA2");
        return false;
    }

    

    

   

    public void chatClosed(ChatC aplicacao) {

        DecrementeMinhasSalasChat();

        boolean resultado = liberar_Sala_Chat();

        if (resultado) {

            Pacote pacote = new Pacote(TipoPacote.ENCERRAR_CHAT, "ENCERRAR_CHAT");
            try {

                aplicacao.enviarMsn(pacote);

            } catch (IOException ex) {
                Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {

            aplicacao.fecharConexao();

        } catch (IOException ex) {
            Logger.getLogger(ChatControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (chatWindow1 != null && e.getSource() == chatWindow1.getEnviarButton()) {

            HashMap<String, Object> message = new HashMap<String, Object>(chatWindow1.getMessageEnterArea());
            //Chama metodo de enviar uma menssagem ao chat
            enviarChatMessagem(message, aplicacao1, chatWindow1);

        } else if (chatWindow2 != null && e.getSource() == chatWindow2.getEnviarButton()) {

            HashMap<String, Object> message = new HashMap<String, Object>(chatWindow2.getMessageEnterArea());
            //Chama metodo de enviar uma menssagem ao chat
            enviarChatMessagem(message, aplicacao2, chatWindow2);

        }  else {
            JOptionPane.showMessageDialog(null, "ActionEvent HOW??");

        }
    }

    @Override
    public void menuSelected(MenuEvent me) {

        

    }

    @Override
    public void windowClosed(WindowEvent we) {
        if (chatWindow1 != null && we.getWindow() == chatWindow1) {
            chatClosed(aplicacao1);
        } else if (chatWindow2 != null && we.getWindow() == chatWindow2) {
            chatClosed(aplicacao2);
        }

    }

    public int getMinhasSalasChat() {
        return minhasSalas_Chat;
    }

    public int DecrementeMinhasSalasChat() {
        this.minhasSalas_Chat--;
        return minhasSalas_Chat;
    }

    public int IncrementeMinhasSalasChat() {
        this.minhasSalas_Chat++;
        return minhasSalas_Chat;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void menuDeselected(MenuEvent me) {
    }

    @Override
    public void menuCanceled(MenuEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }
}//FIM DA CLASSE ChatControlador
