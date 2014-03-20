/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model;

import java.io.Serializable;
import java.net.UnknownHostException;
/**
 *
 * @author Ycaro_2
 */
@SuppressWarnings("serial")
public class Jogador implements Serializable, Comparable<Jogador>{
    private static int IDgenerator;
    private int IDcliente;
    private String login;
    private String senha;

    public Jogador(){
		super();
	}
   
    public Jogador(String login, String senha) {
        this.IDcliente = IDgenerator++;
		this.senha = senha;
                this.login = senha;
	}
    
    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String Senha) {
        this.senha = Senha;
    }
    
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
    
    @Override
	public int compareTo(Jogador j) {
		return this.getLogin().compareTo(j.getLogin());
	}
    
}
