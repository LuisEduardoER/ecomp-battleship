package trunk.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import trunk.model.Tabuleiro;

/**
 *
 * @author Rafael
 */
public class UI_Tabuleiro extends JFrame implements ActionListener {
    
    
    private final JPanel panelfundo = new JPanel();
    //private final Tabuleiro tab;
    private final JPanel panel = new JPanel();
    private final JPanel panel2 = new JPanel();
    private final JPanel rightPanel = new JPanel ();
    private JButton[][] botoes = new JButton[10][10];
    private JButton[][] botoes2 = new JButton[10][10];
    private final JMenu jogarMenu = new JMenu ( "Jogar" );;
    //private final JMenu emoticonsMenu;
    private final JMenuBar menuBar = new JMenuBar();
    private final JTextPane messageArea;
    private final JButton enviarButton;
    private final JTextPane enterArea;
    
    public UI_Tabuleiro(){ 
        super("Ecomp Battleship");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
	setResizable(true);
        setSize( 1280, 800 );
        setLocationRelativeTo(null);
        Container contentPane = getContentPane ();
        Box boxJogo = new Box(BoxLayout.X_AXIS);
        Box boxJogo2 = new Box(BoxLayout.X_AXIS);
        JButton jogarButton = new JButton("Jogar");
        menuBar.add(jogarMenu);
        menuBar.add(jogarButton);
        setJMenuBar(menuBar);
    
        inicializarBotoes();
               
        
         
        
        panel.setSize(new Dimension(100, 100));
        JScrollPane scrollpane = new JScrollPane(panel);
        boxJogo.add(scrollpane, BorderLayout.NORTH);
        
        
        
        
        
        messageArea = new JTextPane();        
        messageArea.setEditable(false);
        messageArea.setPreferredSize(new Dimension(10, 150));
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout(10, 10));
        messagePanel.add(new JScrollPane(messageArea));
        
        enterArea = new JTextPane();      
        enterArea.setPreferredSize(new Dimension(10, 75));
        JScrollPane scroll = new JScrollPane(enterArea);



        Box box = new Box(BoxLayout.X_AXIS);
        box.add(scroll);


        enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(this);
        box.add(enviarButton);
        messagePanel.add(box, BorderLayout.SOUTH);
        
        
        contentPane.add(messagePanel, BorderLayout.SOUTH);
        contentPane.add(boxJogo, BorderLayout.NORTH);
        
        //add(boxJogo2, BorderLayout.EAST);
        
        setVisible(true);
        
    }
    
   
//cria a matriz de botões
    public void inicializarBotoes() {
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {
                
                botoes[i][j] = new JButton();
                botoes[i][j].setSize(5,5);
                botoes[i][j].setIcon(new ImageIcon("mar.jpg"));
                panel.add(botoes[i][j]);
                botoes[i][j].addActionListener(this);
            }
        }
    }
    
    //distribui botões graficamente formando um tabuleiro 10x10
 

    // Ao clicar em um botao, muda a imagem.
    @Override
    public void actionPerformed(ActionEvent event) {
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {
                if (event.getSource() == botoes[i][j]) {
                    Icon fire = new ImageIcon("fire.jpg");
                    if(botoes[i][j].getIcon().toString().equals(fire.toString())){
                        botoes[i][j].setIcon(new ImageIcon("mar.jpg"));
                    }else{
                        botoes[i][j].setIcon(new ImageIcon("fire.jpg"));
                    }
                }
            }
        }
    }
    
     
    public static void main(String args[]) {
        UI_Tabuleiro teste = new UI_Tabuleiro();
        
    }
}
