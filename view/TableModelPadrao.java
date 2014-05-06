package trunk.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import trunk.model.BancoDeJogadores;

/**
 * Classe utilizada para padronizar, de forma tabular, as informações
 * a serem exibidas em <code>JTables</code> ao usuário. 
 * 
 * @author <i>unknow</i>
 */
@SuppressWarnings({"serial", "rawtypes"})
public abstract class TableModelPadrao extends AbstractTableModel {  
    
	   protected String[] colunas;  
	   protected List linhas;     
	     
	   public TableModelPadrao(List linhas) {  
	      setColunas(criarColunas());  
	      this.linhas = linhas;   
	   }
	   
	   public TableModelPadrao(BancoDeJogadores linhas){
		   this(linhas.getBancoDeJogadores());
	   }
	     
	   protected abstract String[] criarColunas();  
	     
	   public int getRowCount() {  
	      if (linhas != null) {  
	         return linhas.size();
	      } else {  
	         return 0;  
	      }  
	   }     
	     
	   public int getColumnCount() {  
	      return colunas.length;  
	   }     
	     
	   public boolean isCellEditable(int row, int col) {  
	      return false;  
	    }     
	     
	    @SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {  
	        return getValueAt(0, c).getClass();  
	    }        
	     
	   public String getColumnName(int col){  
	      return colunas[col];  
	   }  
	  
	   public String[] getColunas() {  
	      return colunas;  
	   }  
	     
	   public void setColunas(String[] colunas) {  
	      this.colunas = colunas;  
	   }  
	     
	   public List getLinhas() {  
	      return linhas;  
	   }  
	  
	   public void setLinhas(List linhas) {  
	      this.linhas = linhas;  
	      fireTableDataChanged();  
	   }  
	     
	        
	}