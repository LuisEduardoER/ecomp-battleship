/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model;

import java.io.Serializable;

/**
 *
 * @author Ycaro_2
 */
public class Jogador implements Serializable {
    private String login;
    private String senha;

    public Jogador(){
		super();
	}
   
    public Jogador(String login, String senha) {
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
    
}
