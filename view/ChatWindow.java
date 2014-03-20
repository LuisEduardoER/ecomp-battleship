/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;
import trunk.control.ChatControlador;

/**
 *
 * @author Ycaro_2
 */
public class ChatWindow extends JFrame {

    private final JMenu compartilharArquivoMenu;
    private final JMenu emoticonsMenu;
    private final JTextPane messageArea;
    private final JTextPane enterArea;
    private final JButton enviarButton;
    private ArrayList <ImageIcon> iconUtilizados = new ArrayList<ImageIcon>();
    ArrayList <ImageIcon>  emoticonsRecebidos;
    HashMap<String, Object> message = new HashMap<String, Object>();
    EmoticonView emoticonView;

    public JMenu getCompartilharArquivoMenu() {
        return compartilharArquivoMenu;
    }

    public JMenu getEmoticonsMenu() {
        return emoticonsMenu;
    }

    public JButton getEnviarButton() {
        return enviarButton;
    }

    public void clearEnterArea() {
        enterArea.setText("");
        iconUtilizados = new ArrayList<ImageIcon>();
    }

    public HashMap<String,Object> getMessageEnterArea() {
        
        String messageEnterArea = new String(enterArea.getText());
        message.put("mensagem", messageEnterArea);
        return message;
 }

    public void setEmoticonEnterArea(ImageIcon icon) throws BadLocationException {

            StyledDocument doc = (StyledDocument) enterArea.getDocument();


            int end = Utilities.getWordEnd(enterArea, doc.getLength());
            final SimpleAttributeSet attrs = new SimpleAttributeSet();

            StyleConstants.setIcon(attrs, icon);
        
            doc.insertString(end, "Ø", attrs);//Alt+157 Numpad
            
            iconUtilizados.add(icon);
            message.put("emoticon", iconUtilizados);
    }

    public void setMessageArea(final String from, final HashMap<String,Object> message) {
            final String messageTxt = (String) message.get("mensagem");
            System.out.println(messageTxt);
            if(message.get("emoticon")!=null){
            emoticonsRecebidos =  new ArrayList<ImageIcon>((ArrayList<ImageIcon>) message.get("emoticon"));
            }
            SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                try {   
                        StyledDocument doc = (StyledDocument) messageArea.getDocument();                        
                        int end = Utilities.getWordEnd(messageArea, doc.getLength());
                        char c = 'Ø';
                        int j = 0;
                        
                        doc.insertString(end, "\n"+ from + " >> ", null);
                        
                        for (int i = 0; i < messageTxt.length(); i++) { 
                            
                        if (messageTxt.charAt(i) == c) {
                            
                           SimpleAttributeSet attrs = new SimpleAttributeSet();
                          
                            StyleConstants.setIcon(attrs, emoticonsRecebidos.get(j));
                            end = Utilities.getWordEnd(messageArea, doc.getLength());
                            doc.insertString(end, "Ø", attrs);  
                            j++;
                            }else{
                            
                                String s = String.valueOf(messageTxt.charAt(i));
                                end = Utilities.getWordEnd(messageArea, doc.getLength());
                                doc.insertString(end, s , null);
                            }
                        }                        
                        
                        
                } catch (BadLocationException ex) {
                    Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);                
                }
             }
        });
}

    public ChatWindow(ChatControlador controlador) {
        super("Chat Ecomp Calendar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setSize( 300, 400 );
        addWindowListener(controlador);
        
        compartilharArquivoMenu = new JMenu ( "Compartilhar Arquivo" );
        compartilharArquivoMenu.addMenuListener(controlador);
        
        emoticonsMenu = new JMenu("Emoticons");
        emoticonsMenu.addMenuListener(controlador);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(compartilharArquivoMenu);
        menuBar.add(emoticonsMenu);
        setJMenuBar(menuBar);


        messageArea = new JTextPane();        
        messageArea.setEditable(false);       
       

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout(10, 10));
        messagePanel.add(new JScrollPane(messageArea));

        enterArea = new JTextPane();      
        enterArea.setPreferredSize(new Dimension(10, 75));
        JScrollPane scroll = new JScrollPane(enterArea);



        Box box = new Box(BoxLayout.X_AXIS);
        box.add(scroll);


        enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(controlador);
        box.add(enviarButton);
        messagePanel.add(box, BorderLayout.SOUTH);
        add(messagePanel, BorderLayout.CENTER);


        setVisible(true);
    }
    
    
        
       

    
}
