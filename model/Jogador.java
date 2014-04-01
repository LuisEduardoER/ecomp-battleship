package trunk.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;
@SuppressWarnings("serial")
public class Jogador extends Usuario implements Serializable{
	
	
	private Barco [] barcos;
	private List pontos;
	private InetAddress endereco;
    private int porta;
    
   
        
        
	public Jogador(String login, String senha, InetAddress endereco, int porta){
	super(login, senha);
	this.endereco=endereco;
        this.porta=porta;
     }
	
        public Jogador(String login, String senha){
	
            super(login, senha);
        }
	
        public void contruirBarcos(){
        
        barcos = new Barco[5];
        barcos[0] = Barco.constroiBarco(Barco.submarino);
        barcos[1] = Barco.constroiBarco(Barco.fragata);
        barcos[2] = Barco.constroiBarco(Barco.cTorpedo);
        barcos[3] = Barco.constroiBarco(Barco.destroyer);
        barcos[4] = Barco.constroiBarco(Barco.pAviao);
        
        }

    public Barco[] getBarcos() {
			return barcos;
		}

		public void setBarcos(Barco[] barcos) {
			this.barcos = barcos;
		}

	public InetAddress getEndereco() {
        return endereco;
    }

    public int getPorta() {
        return porta;
    }

    public void setEndereco(InetAddress endereco) {
        this.endereco = endereco;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
    
   
    
}
