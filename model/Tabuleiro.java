package trunk.model;

import java.util.Random;

import javax.swing.JOptionPane;

public class Tabuleiro {

	static int tabuleiro[][];
        //variáveis que servem para identificar os barcos
	static Barco e1;
	static Barco e2;
	static Barco e3;
	static Barco e4;
	static Barco e5;
	static int destruidos =0;
	static Jogador jog1;
        //variáveis que servem para somar o tamanho das embarcações na hora do naufrágio
	static int b1=0;
	static int b2=0;
	static int b3=0;
	static int b4=0;
	static int b5=0;

	/*public static void main(String[] args) {
		
		
		tabuleiro = new int [10][10];
		
		inicializaEmbarcacao();
		Sorteio();
		
	
		mostraTabuleiro(tabuleiro);
		
			
}*/

	public Tabuleiro() {

		tabuleiro = new int[10][10];
		// Preenchendo todas as posi��es da matriz com valor zero
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				tabuleiro[i][j] = 0;

			}// for in
		}// for out

	}// construtor
	

	//Inicializando as embarcações
	public static void inicializaEmbarcacao(){
		Tabuleiro.e1 = new Barco("Submarino",1);
		Tabuleiro.e2 = new Barco("Fragata",2);
		Tabuleiro.e3 = new Barco("Contra Torpedo",3);
		Tabuleiro.e4 = new Barco("Destroyer",4);
		Tabuleiro.e5 = new Barco("Porta Avi�o",5);
	}
	public static void Sorteio() {

		for (int i = 1; i < 6; i++) {

			// Gera numeros aleat�rios entre 0 e 9
			int linha = (int) Math.random() * 10;
			int coluna = (int) Math.random() * 10;

			// Inser��o do submarino (1 quadrado)        AQUIIIIIIIIII
			if(i==1){
			if (tabuleiro[linha][coluna] == 0) {

				tabuleiro[linha][coluna] = i;
				i++;
			}// if
			}

			//Para todas as embarca��es maiores que 1 deve-se escolher seu sentido(horizontal ou vertical)
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
										// inser��o
						for(int n=0;n<i;n++){
						tabuleiro[linha+n][coluna] = i;
						}						
						i++;
					}// if condi��o de inser��o

				}

			}
		}// for
	}

	
	
	
	// M�todo que escolhe se os barcos ser�o distribuidos na Horizontal(true) ou
	// Vertical(false)
	public static boolean Sentido() {

		Random gerador = new Random();
		boolean resultado = gerador.nextBoolean();

		return resultado;
	}

	// M�todo que valida a inser��o de um barco na horizontal
	public static boolean VerificaHorizontal(int l, int c, int num) {

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

	// M�todo que valida a inser��o de um barco na vertical
	public static boolean VerificaVertical(int l, int c, int num) {

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
	
	
	/*public static void mostraTabuleiro(int[][] tabuleiro){
		 
	    }
	*/
	
	public  Boolean darTiro(int linha, int coluna) {
		
		
		for(int i=1;i<6;i++){
			if(tabuleiro[linha][coluna] == i){// se o conteudo da linha for igual a 1
				if(tabuleiro[linha][coluna] == e1.getTamanho()){//numero do submarino
					//e1++;
					destruidos++;
					JOptionPane.showMessageDialog(null, "Fogo");
					JOptionPane.showMessageDialog(null, "Submarino Afundou");
				}else if(tabuleiro[linha][coluna] == e2.getTamanho()){//numero do fragata
					b2++;//
					JOptionPane.showMessageDialog(null, "Fogo");
					if(b2 == 2){//quando a quantidade de b2 for igual ao tamanho de 2
						destruidos++;
						JOptionPane.showMessageDialog(null, "Fragata Afundou");
					}
				}else if(tabuleiro[linha][coluna] == e3.getTamanho()){
					b3++;
					JOptionPane.showMessageDialog(null, "Fogo");
					if(b3 == 3){//quando a quantidade de b3 for igual ao tamanho de 3
						destruidos++;
						JOptionPane.showMessageDialog(null, "Contra Torpedo Afundou");
					}
				}else if(tabuleiro[linha][coluna] == e4.getTamanho()){
					b4++;
					JOptionPane.showMessageDialog(null, "Fogo");
					if(b4 == 4){//quando a quantidade de b4 for igual ao tamanho de 4
						destruidos++;
						JOptionPane.showMessageDialog(null, "Destroyer Afundou");
					}
				}else if(tabuleiro[linha][coluna] == e5.getTamanho()){
					b5++;
					JOptionPane.showMessageDialog(null, "Fogo");
					if(b5 == 5){//quando a quantidade de b5 for igual ao tamanho de 5
						destruidos++;
						JOptionPane.showMessageDialog(null, "Porta-Aviao Afundou");
						
					}
				}
				
				return true;
			}
		
        }
		return false;
                
	}

		public int getDestruidos(){
			return destruidos;
		}


}
