package trunk.model;

import java.util.List;

public class Jogador extends Usuario{
	
	
	private Barco [] barcos;
	private List pontos;
	private Tabuleiro tab;
	
	public Jogador(String login, String senha){
		
	super(login, senha);
	
	barcos = new Barco[5];
	
	 barcos[0] = Barco.constroiBarco(Barco.submarino, this);
     barcos[1] = Barco.constroiBarco(Barco.fragata, this);
     barcos[2] = Barco.constroiBarco(Barco.cTorpedo, this);
     barcos[3] = Barco.constroiBarco(Barco.destroyer, this);
     barcos[4] = Barco.constroiBarco(Barco.pAviao, this);
	
		
	}
	
	
	
}
