package trunk.model;

public enum TipoStatusConvidado {
	PENDENTE, CONFIRMADO, REJEITADO;
	
	@Override
	public String toString(){
		switch (this) {
		case PENDENTE:
			return "Pendente";
		case CONFIRMADO:
			return "Confirmado";
		case REJEITADO:
			return "Rejeitado";
		default:
			return "";
		}
	}
}
