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
   
	
	public Barco(String nome, int tamanho){
		this.nome= nome;
		this.tamanho = tamanho;
		
		
		
		
	}
	
	  public static Barco constroiBarco(int tamanho) {
	        switch (tamanho) {
	            case submarino:
	                return new Barco("Submarino", 1);
	            case fragata:
	                return new Barco("Fragata", 2);
	            case cTorpedo:
	                return new Barco ("Contra Torpedeiros", 3);
	            case destroyer:
	                return new Barco ("Destroyer", 4);
	            case pAviao:
	                return new Barco ("Porta Aviao", 5);
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

	    

	
	
}
