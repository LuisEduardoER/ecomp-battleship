
package trunk.control;


public class ExecutaServer {

   
    public static void main(String[] args) {  
        
        //Aqui ocorre a execução do servidor
        ProgramServer chamaServidor = new ProgramServer(26147); //executa o servidor, basta passar por parâmetro a porta
        try {
            chamaServidor.ExecutarServidor();
        } catch (ClassNotFoundException ex) {
            System.out.println("Não executou o servidor");
            Logger.getLogger(ExecutaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fim da main
}// Fim da Classe