/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class TelaServer extends JFrame{
    
    private JTextArea displayArea;//apresenta informações
    
    public  TelaServer() {

        super("Programa Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        displayArea = new JTextArea(); //Cria display area
        add(new JScrollPane (displayArea),BorderLayout.CENTER);
        setSize(300,300); //Configura o tamanho da janela

    }
    
    //manipula displayArea na thread de despacho de eventos
    public void exibirMessagem(final String MostrarMsn) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                displayArea.append(MostrarMsn);
            }//fim do método run
        }//fim da classe interna anônima
                );
    }
}// Fim da TelaServer
