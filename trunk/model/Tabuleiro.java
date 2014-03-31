package trunk.model;

import java.util.Random;

import javax.swing.JOptionPane;

public class Tabuleiro {

	private int tabuleiro[][];
	static Barco e1;
	static Barco e2;
	static Barco e3;
	static Barco e4;
	static Barco e5;
	static int destruidos =0;
	static Jogador jog1;
	static int b1=0;
	static int b2=0;
	static int b3=0;
	static int b4=0;
	static int b5=0;


	public Tabuleiro() {

		tabuleiro = new int[10][10];
		// Preenchendo todas as posições da matriz com valor zero
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				tabuleiro[i][j] = 0;

			}// for in
		}// for out

	}// construtor
	
	public void inicializaEmbarcacao(){
		this.e1 = new Barco("Submarino",1,jog1);
		this.e2 = new Barco("Fragata",2,jog1);
		this.e3 = new Barco("Contra Torpedo",3,jog1);
		this.e4 = new Barco("Destroyer",4,jog1);
		this.e5 = new Barco("Porta Avião",5,jog1);

		
	}
/*	public static void main(String[] args) {
	
			Sorteio();
		//	inicializaEmbarcacoes( tabuleiro);
		
		//do{
			mostraTabuleiro(tabuleiro);
			
				
			
		//}while(destruidos != 5);

	}*/
	public void Sorteio() {

		for (int i = 1; i < 6; i++) {

			// Gera numeros aleatórios entre 0 e 9
			int linha = (int) Math.random() * 10;
			int coluna = (int) Math.random() * 10;

			// Inserção do submarino (1 quadrado)        AQUIIIIIIIIII
			if(i==1){
			if (tabuleiro[linha][coluna] == 0) {

				tabuleiro[linha][coluna] = i;
				i++;
			}// if
			}

			//Para todas as embarcações maiores que 1 deve-se escolher seu sentido(horizontal ou vertical)
			if (i > 1) {

				if (Sentido()) {// Se for verdadeiro == Horizontal

					if (VerificaHorizontal(linha, coluna, i)) {// valida a
																// inserção
						for(int m=0;m<i;m++){
						tabuleiro[linha][coluna+m] = i;
						}
					
						i++;
					}// if condição de inserção

				}// if sentido

				else {

					if (VerificaVertical(linha, coluna, i)) {// valida a
																// inserção
						for(int n=0;n<i;n++){
						tabuleiro[linha+n][coluna] = i;
						}						
						i++;
					}// if condição de inserção

				}

			}
		}// for
	}

	
	
	
	// Método que escolhe se os barcos serão distribuidos na Horizontal(true) ou
	// Vertical(false)
	public boolean Sentido() {

		Random gerador = new Random();
		boolean resultado = gerador.nextBoolean();

		return resultado;
	}

	// Método que valida a inserção de um barco na horizontal
	public boolean VerificaHorizontal(int l, int c, int num) {

		if (tabuleiro[l][c] == 0 && tabuleiro[l][c - 1] == 0
				&& tabuleiro[l - 1][c] == 0 && tabuleiro[l + 1][c] == 0
				&& tabuleiro[l][c + (1 * num)] == 0) {

			int k = 0;

			while (k < num) {

				if (tabuleiro[l][c + k] == 0 && tabuleiro[l - 1][c + k] == 0
						&& tabuleiro[l + 1][c + k] == 0
						&& tabuleiro[l][(c + k) + (1 * num)] == 0)
					k++;

			}// while

			return true;

		}// if out

		return false;

	}

	// Método que valida a inserção de um barco na vertical
	public boolean VerificaVertical(int l, int c, int num) {

		if (tabuleiro[l][c] == 0 && tabuleiro[l - 1][c] == 0
				&& tabuleiro[l][c - 1] == 0 && tabuleiro[l][c + 1] == 0
				&& tabuleiro[l + (1 * num)][c] == 0) {

			int k = 0;

			while (k < num) {

				if (tabuleiro[l + k][c] == 0 && tabuleiro[(l + k) + 1][c] == 0
						&& tabuleiro[l + k][c - 1] == 0
						&& tabuleiro[l + k][c + 1] == 0
						&& tabuleiro[(l + k) + (1 * num)][c] == 0)
					k++;

			}// while

			return true;
		}

		return false;
	}
	
	
	public void mostraTabuleiro(int[][] tabuleiro){
		 int e5,e4, e3, e2,e1;
		e5 =e4 = e3 = e2 =e1 =0;
	        for(int linha=0 ; linha < 10 ; linha++ ){
	          
	            for(int coluna=0 ; coluna < 10 ; coluna++ ){
	            	if(tabuleiro[linha][coluna] == 0 ){
	            		System.out.println("E1 =" + linha + " x " + coluna );
	            		 e1++;
	            	}else if(tabuleiro[linha][coluna]== 1 ){
	            		System.out.println("E2 =" + linha + " x " + coluna );
	            		 e2++;
	            	}else if(tabuleiro[linha][coluna]== 2 ){
	            		System.out.println("E3 =" + linha + " x " + coluna );
	            		 e3++;
	            	}else if(tabuleiro[linha][coluna]== 3 ){
	            		System.out.println("E4 =" + linha + " x " + coluna );
	            		 e4++;
	            	}else if(tabuleiro[linha][coluna]== 4 ){
	            		System.out.println("E5 =" + linha + " x " + coluna );
	            		 e5++;
	            	}
	            		
	            	}
	         }
	        System.out.println("E1 = " + e1 + "E2 = " + e2 + "E3 = " + e3 + "E4 = " + e4 + "E5 = " + e5);
	        System.out.println("\t0\t1 \t2 \t3 \t4 \t5 \t6 \t7 \t8 \t9");
	        System.out.println();
	        for(int linha=0 ; linha < 10 ; linha++ ){
	            System.out.print((-1 +linha+1)+"");
	            for(int coluna=0 ; coluna < 10 ; coluna++ ){
	                if(tabuleiro[linha][coluna]==-1){
	                    System.out.print("\t"+"-");
	                }else if(tabuleiro[linha][coluna]== 0 || tabuleiro[linha][coluna]== 1 || tabuleiro[linha][coluna]== 2 || tabuleiro[linha][coluna]== 3 || tabuleiro[linha][coluna]== 4){
	                    System.out.print("\t"+"E");
	                }else if(tabuleiro[linha][coluna]==1){
	                    System.out.print("\t"+"X");
	                }
	                
	            }
	            System.out.println();
	        }

	    }
	
public  Boolean darTiro(int linha, int coluna) {
				
		
		for(int i = 1; i <= 5; i++){
			if(tabuleiro[linha][coluna] == i){
				if(tabuleiro[linha][coluna] == e1.getTamanho()){
					//e1++;
					destruidos++;
					JOptionPane.showMessageDialog(null, "Fogo");
					JOptionPane.showMessageDialog(null, "Submarino Naufragou");
				}else if(tabuleiro[linha][coluna] == e2.getTamanho()){
					b2++;
					JOptionPane.showMessageDialog(null, "Fogo");
					if(b2 == 2){
						destruidos++;
						JOptionPane.showMessageDialog(null, "Fragata Naufragou");
					}
				}else if(tabuleiro[linha][coluna] == e3.getTamanho()){
					b3++;
					JOptionPane.showMessageDialog(null, "Fogo");
					if(b3 == 3){
						destruidos++;
						JOptionPane.showMessageDialog(null, "Contra Torpedo Naufragou");
					}
				}else if(tabuleiro[linha][coluna] == e4.getTamanho()){
					b4++;
					JOptionPane.showMessageDialog(null, "Fogo");
					if(b4 == 4){
						destruidos++;
						JOptionPane.showMessageDialog(null, "Destroyer Naufragou");
					}
				}else if(tabuleiro[linha][coluna] == e5.getTamanho()){
					b5++;
					JOptionPane.showMessageDialog(null, "Fogo");
					if(b5 == 5){
						destruidos++;
						JOptionPane.showMessageDialog(null, "Porta-Aviao Naufragou");
						
					}
				}
				if(destruidos == 5){
					
				}
				return true;
			}
		}
		return false;
		
	}

		 


}
