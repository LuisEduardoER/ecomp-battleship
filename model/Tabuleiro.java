package trunk.model;

import java.util.Random;

public class Tabuleiro {

	private int tabuleiro[][];

	public Tabuleiro() {

		tabuleiro = new int[10][10];
		// Preenchendo todas as posi��es da matriz com valor zero
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				tabuleiro[i][j] = 0;

			}// for in
		}// for out

	}// construtor

	public void Sorteio() {

		for (int i = 1; i < 6; i++) {

			// Gera numeros aleat�rios entre 0 e 9
			int linha = (int) Math.random() * 10;
			int coluna = (int) Math.random() * 10;

			// Inser��o do submarino (1 quadrado)
			if (tabuleiro[linha][coluna] == 0) {

				tabuleiro[linha][coluna] = i;
				i++;
			}// if

			if (i > 2) {

				if (Sentido()) {// Se for verdadeiro == Horizontal

					if (VerificaHorizontal(linha, coluna, i)) {// valida a
																// inser��o
						tabuleiro[linha][coluna] = i;
						i++;
					}// if condi��o de inser��o

				}// if sentido

				else {

					if (VerificaVertical(linha, coluna, i)) {// valida a
																// inser��o
						tabuleiro[linha][coluna] = i;
						i++;
					}// if condi��o de inser��o

				}

			}
		}// for
	}

	
	
	
	// M�todo que escolhe se os barcos ser�o distribuidos na Horizontal(true) ou
	// Vertical(false)
	public boolean Sentido() {

		Random gerador = new Random();
		boolean resultado = gerador.nextBoolean();

		return resultado;
	}

	// M�todo que valida a inser��o de um barco na horizontal
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

	// M�todo que valida a inser��o de um barco na vertical
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

}
