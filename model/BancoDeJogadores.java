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
public class BancoDeJogadores implements Serializable, Iterable<Usuario> {

	private List<Usuario> bancoDeJogadores;

	public List<Usuario> getBancoDeJogadores() {
		return bancoDeJogadores;
	}
        
        public Usuario getJogador(int i) {
                Usuario jl = bancoDeJogadores.get(i);
		return jl;
	}
        
	public BancoDeJogadores() {
		bancoDeJogadores = new ArrayList<Usuario>();
		Collections.sort(bancoDeJogadores);
	}

	public BancoDeJogadores(BancoDeJogadores novoBanco) {
		bancoDeJogadores = new ArrayList<Usuario>();
		bancoDeJogadores.addAll(novoBanco.getBancoDeJogadores());
		Collections.sort(bancoDeJogadores);
	}
        
	public boolean inserirJogador(Usuario novoJogador) { // insere cliente na lista
		if (!contem(novoJogador)) {
			return bancoDeJogadores.add(novoJogador);
		} else {
			return false;
		}
	}
	
	public boolean inserirJogadores(BancoDeJogadores jogadores){
		return bancoDeJogadores.addAll(jogadores.bancoDeJogadores);
	}

	public boolean autentica(Usuario jogador) { //Busca jogador na lista de jogadores

		for (Usuario aux : bancoDeJogadores) {
			if (aux.getLogin().equals(jogador.getLogin())
					&& aux.getSenha().equals(jogador.getSenha())) {
				
				return true;
			}
		}
		return false;
	}    

	public boolean excluirJogador(Usuario jogador) { //Exclui cliente da lista de jogadores

		for (Usuario aux : bancoDeJogadores) {
			if (aux.getLogin().equals(jogador.getLogin())
					&& aux.getSenha().equals(jogador.getSenha())) {
				bancoDeJogadores.remove(aux);

				return true;
			}
		}
		return false;
	}    

	
	public boolean contem(Usuario jogador) { //Busca cliente na lista de jogadores
		for (Usuario aux : bancoDeJogadores) {
			if (aux.getLogin().equals(jogador.getLogin())) {
				return true;
			}
		}
		return false;
	}
	
	
	public Usuario getJogador(String login) {
		for (Usuario jogador : bancoDeJogadores) {
			if (jogador.getLogin().equals(login)) {
				return jogador;
			}
		}
		return null;
	}

	@Override
	public Iterator<Usuario> iterator() {
		return bancoDeJogadores.iterator();
	}

	public int size() {
		return bancoDeJogadores.size();
	}
}
