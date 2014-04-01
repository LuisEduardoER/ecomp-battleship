package tabuleirobattleship;

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
    
    JPanel panelfundo = new JPanel();
    Tabuleiro tab;
    JPanel panel = new JPanel();
    JButton[][] botoes = new JButton[10][10];

    //cria a matriz de botões
    public void inicializarBotoes() {
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {
                botoes[i][j] = new JButton();
            }
        }
    }

    //determina o tamanho de cada botão
    public void dimensionarBotoes() {
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {
                botoes[i][j].setPreferredSize(new Dimension(25, 25));
            }
        }
    }
    
    //distribui botões graficamente formando um tabuleiro 10x10
    public void distribuirBotoes() {
        
        panelfundo.setLayout(new GridLayout(100, 100)); 
        panel.setLayout(new GridLayout(10, 10));
        panelfundo.setPreferredSize(new Dimension(100, 100));
        panelfundo.add(panel);
        
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {
                panel.add(botoes[i][j]);
                botoes[i][j].setIcon(new ImageIcon("mar.jpg"));
            }
        }
        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        pack();
        
        setVisible(true);     
        }

    public void adicionarEvento() {
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {
                botoes[i][j].addActionListener(this);
            }
        }
    }

    // Ao clicar em um botao, muda a imagem.
    @Override
    public void actionPerformed(ActionEvent event) {
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes[i].length; j++) {
                if (event.getSource() == botoes[i][j]) {
                    botoes[i][j].setIcon(new ImageIcon("fire.jpg"));
                }
            }
        }
    }

    public static void main(String args[]) {
        UI_Tabuleiro teste = new UI_Tabuleiro();
        teste.inicializarBotoes();
        teste.adicionarEvento();
        teste.dimensionarBotoes();
        teste.distribuirBotoes();
    }
}
