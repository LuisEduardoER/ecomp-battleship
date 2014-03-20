/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Karine
 */
@SuppressWarnings("serial")
public class BancoDeJogadores implements Serializable, Iterable<Jogador> {

	private List<Jogador> bancoDeJogadores;

	public List<Jogador> getBancoDeJogadores() {
		return bancoDeJogadores;
	}
        
        public Jogador getJogador(int i) {
                Jogador jl = bancoDeJogadores.get(i);
		return jl;
	}
        
	public BancoDeJogadores() {
		bancoDeJogadores = new ArrayList<Jogador>();
		Collections.sort(bancoDeJogadores);
	}

	public BancoDeJogadores(BancoDeJogadores novoBanco) {
		bancoDeJogadores = new ArrayList<Jogador>();
		bancoDeJogadores.addAll(novoBanco.getBancoDeJogadores());
		Collections.sort(bancoDeJogadores);
	}
        
	public boolean inserirJogador(Jogador novoJogador) { // insere cliente na lista
		if (!contem(novoJogador)) {
			return bancoDeJogadores.add(novoJogador);
		} else {
			return false;
		}
	}
	
	public boolean inserirJogadores(BancoDeJogadores jogadores){
		return bancoDeJogadores.addAll(jogadores.bancoDeJogadores);
	}

	public boolean autentica(Jogador jogador) { //Busca jogador na lista de jogadores

		for (Jogador aux : bancoDeJogadores) {
			if (aux.getLogin().equals(jogador.getLogin())
					&& aux.getSenha().equals(jogador.getSenha())) {
				
				return true;
			}
		}
		return false;
	}    

	public boolean excluirJogador(Jogador jogador) { //Exclui cliente da lista de jogadores

		for (Jogador aux : bancoDeJogadores) {
			if (aux.getLogin().equals(jogador.getLogin())
					&& aux.getSenha().equals(jogador.getSenha())) {
				bancoDeJogadores.remove(aux);

				return true;
			}
		}
		return false;
	}    

	
	public boolean contem(Jogador jogador) { //Busca cliente na lista de jogadores
		for (Jogador aux : bancoDeJogadores) {
			if (aux.getLogin().equals(jogador.getLogin())) {
				return true;
			}
		}
		return false;
	}
	
	
	public Jogador getJogador(String login) {
		for (Jogador jogador : bancoDeJogadores) {
			if (jogador.getLogin().equals(login)) {
				return jogador;
			}
		}
		return null;
	}

	@Override
	public Iterator<Jogador> iterator() {
		return bancoDeJogadores.iterator();
	}

	public int size() {
		return bancoDeJogadores.size();
	}
}
