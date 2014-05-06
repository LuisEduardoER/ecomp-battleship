package trunk.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Convidado implements Serializable, Comparable<Convidado>{
	private static int IDgenerator;
	private int IDconvidado;
	private String nome;
	private String email;
	private TipoStatusConvidado status;

	public Convidado(String nome, String email){
		this.IDconvidado = IDgenerator++;
		this.nome = nome;
		this.email = email;
		this.status = TipoStatusConvidado.PENDENTE;
	}
	
	public Convidado(String nome, String email, String senha, TipoStatusConvidado status){
		this.IDconvidado = IDgenerator++;
		this.nome = nome;
		this.email = email;
		this.status = status;
	}

	public int getIDcliente() {
		return IDconvidado;
	}

	public void setNome(String nom){
		nome = nom;
	}

	public String getNome(){    
		return nome;
	}

	public void setEmail(String emai){
		email = emai;

	}

	public String getEmail(){
		return email;
	}

	public void setStatus(TipoStatusConvidado status){
		this.status = status;
	}

	public TipoStatusConvidado getStatus(){
		return this.status;
	}

	@Override
	public int compareTo(Convidado o) {
		return this.getNome().compareTo(o.getNome());
	}

}

