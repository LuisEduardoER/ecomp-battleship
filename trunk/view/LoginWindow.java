package trunk.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import trunk.control.ClienteControlador;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame{
	private JTextField		loginCampo;
	private JPasswordField	senhaCampo;
	private JTextField		servidorCampo;
	private JLabel			loginLabel;
	private JLabel			senhaLabel;
	private JLabel			servidorLabel;
	private JButton			okButton;
	private JButton			cancelButton;
	private JLabel			cadastro;
	
	public LoginWindow(ClienteControlador controlador) {
		super("EComp Battleship");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		
		loginCampo = new JTextField();
		senhaCampo = new JPasswordField();
		servidorCampo = new JTextField();
		
		loginCampo.addKeyListener(controlador);
		senhaCampo.addKeyListener(controlador);
		servidorCampo.addKeyListener(controlador);
		
		loginLabel = new JLabel("Login:");
		senhaLabel = new JLabel("Senha:");
		servidorLabel = new JLabel("Servidor:");
		
		okButton = new JButton();
		okButton.setText("Entrar");
		okButton.addActionListener(controlador);
		
		cancelButton = new JButton();
		cancelButton.setText("Cancelar");
		cancelButton.addActionListener(controlador);
		
		cadastro = new JLabel("Cadastre-se");
		cadastro.setForeground(new Color(0, 0, 255));
               cadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cadastro.addMouseListener(controlador);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(cancelButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(servidorLabel)
                            .addComponent(senhaLabel)
                            .addComponent(loginLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(servidorCampo, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(senhaCampo, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(loginCampo, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))))
                .addGap(45, 45, 45))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(cadastro)
                .addGap(96, 96, 96))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginLabel)
                    .addComponent(loginCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senhaLabel)
                    .addComponent(senhaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(servidorLabel)
                    .addComponent(servidorCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addGap(27, 27, 27))
        );
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		setSize(new Dimension(275, 220));
		pack();
        setLocationRelativeTo(null);
		setVisible(true);
	}

	public JLabel getCadastro() {
		return cadastro;
	}

	public JTextField getLoginCampo() {
		return loginCampo;
	}

	public JTextField getSenhaCampo() {
		return senhaCampo;
	}

	public JTextField getServidorCampo() {
		return servidorCampo;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}
}
