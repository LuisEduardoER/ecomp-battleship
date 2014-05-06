package trunk.model;

public enum TipoStatus {
	ONLINE,OFFLINE,OCUPADO;
	
	@Override
	public String toString(){
		switch (this) {
		case ONLINE:
			return "ONLINE";
		case OFFLINE:
			return "OFFLINE";
		case OCUPADO:
			return "OCUPADO";		
		default:
			return "";
		}
	}
}
