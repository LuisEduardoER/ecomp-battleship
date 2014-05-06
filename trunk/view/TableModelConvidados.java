package trunk.view;

import trunk.model.BancoDeJogadores;
import trunk.model.Jogador;
import trunk.model.Convidado;

/**
 * Classe utilizada para padronizar, de forma tabular, as informações
 * de tokens a serem exibidas em <code>JTables</code> ao usuário. 
 * 
 * @author Jody Matos, Alenilson
 * @version 1.0
 * @since	10/18/2011
 * 
 * <dt><b>History:</b></dt>
 * 	<dd>1.0: Creation of first methods</dd>
 */
@SuppressWarnings({ "serial", "unchecked" })
public class TableModelConvidados extends TableModelPadrao{

	public TableModelConvidados(BancoDeJogadores linhas) {
		super(linhas.getBancoDeJogadores());
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Convidado cliente  = (Convidado) linhas.get(rowIndex);
		switch (columnIndex){
		case 0:
			return cliente.getNome();
		case 1:
			return cliente.getStatus();
		default:
			return null;
		}
	}

	@Override
	protected String[] criarColunas() {
		return new String[]{"Nome","Situação"};
	}
	
	public Object getSelectedObject(int row){
		return linhas.get(row);
	}
}
