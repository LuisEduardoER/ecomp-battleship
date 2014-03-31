package trunk.model;

import java.awt.Point;

public class Barco {
	
	//Inicializando as embarcações de acordo com a quantidade de quadrados que ocupam no tabuleiro
	public static final int submarino = 1;
	public static final int fragata =2;
	public static final int cTorpedo =3;
	public static final int destroyer =4;
	public static final int pAviao =5;
	
    private final String nome;
    private final int tamanho;
    private final Jogador jogador;
    private Point posicao;
    private int orientacao;
	
	public Barco(String nome, int tamanho, Jogador jogador){
		this.nome= nome;
		this.tamanho = tamanho;
		this.jogador = jogador;
		
		
		
	}
	
	  public static Barco constroiBarco(int tamanho, Jogador jog) {
	        switch (tamanho) {
	            case submarino:
	                return new Barco("Submarino", 1, jog);
	            case fragata:
	                return new Barco("Fragata", 2, jog);
	            case cTorpedo:
	                return new Barco ("Contra Torpedeiros", 3, jog);
	            case destroyer:
	                return new Barco ("Destroyer", 4, jog);
	            case pAviao:
	                return new Barco ("Porta Aviao", 5, jog);
	            default:
	                return null;
	        }
	    }

	    public String getNome() {
	        return nome;
	    }

	    public int getTamanho() {
	        return tamanho;
	    }

	    public Jogador getJogador() {
	        return jogador;
	    }


	
	
}
