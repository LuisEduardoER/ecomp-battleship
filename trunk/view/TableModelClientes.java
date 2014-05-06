package trunk.view;

import java.util.List;

import trunk.model.BancoDeJogadores;
import trunk.model.Jogador;

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
public class TableModelClientes extends TableModelPadrao{

	public TableModelClientes(BancoDeJogadores linhas) {
		super(linhas);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Jogador cliente  = (Jogador) linhas.get(rowIndex);
		switch (columnIndex){
		case 0:
			return cliente.getLogin();
		case 1:
			return cliente.getStatus();
		default:
			return null;
		}
	}

	@Override
	protected String[] criarColunas() {
		return new String[]{"Usuários","Status"};
	}
	
	public Object getSelectedObject(int row){
		return linhas.get(row);
	}
}
