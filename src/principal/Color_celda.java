package principal;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Color_celda extends JTable { 
	
	
	
	private static final long serialVersionUID = 1L;

	public Color_celda(DefaultTableModel modelo) {
		
		this.setModel(modelo);
		
		
			
		}

	@Override
	public Component prepareRenderer (TableCellRenderer renderer, int rowindex, int columindex) {
		
		Component componente = super.prepareRenderer(renderer, rowindex, columindex);
		
		if(getValueAt(rowindex, columindex).getClass().equals(Integer.class)) { 
			
			int bloque_mem = Integer.parseInt(this.getValueAt(rowindex, columindex).toString());
			
			if(bloque_mem == 32 ) {
				componente.setBackground(Color.green); 
				componente.setForeground(Color.WHITE);
				
		}else if(bloque_mem == 64) { 
			
			componente.setBackground(Color.BLUE); 
			componente.setForeground(Color.WHITE);
		}else {  
			
			
			componente.setBackground(Color.RED); 
			componente.setForeground(Color.WHITE);
			
		}
			
			
		} else {  
			
			
			componente.setBackground(Color.WHITE); 
			componente.setForeground(Color.BLACK);
			
		}
		
		return componente; 
		
	}	
	
	

}
