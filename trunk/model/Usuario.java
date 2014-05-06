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
public class Usuario implements Serializable, Comparable<Usuario>{
    private static int IDgenerator;
    private int IDcliente;
    private String login;
    private String senha;
    private TipoStatus status;
    private int qtd_tiros;
    
    public Usuario(){
		super();
	}
   
    public Usuario(String login, String senha) {
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
    
    public void setStatus(TipoStatus status){
		this.status = status;
	}

    public TipoStatus getStatus(){
		return this.status;
    }
        
    @Override
    public int compareTo(Usuario j) {
		return this.getLogin().compareTo(j.getLogin());
    }
    
}
