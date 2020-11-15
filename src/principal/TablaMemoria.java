package principal;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import planificador.Recursos;

//Clase TablaMemoria muestra el estado del recursos memoria en el SSOP
public class TablaMemoria extends JTable{
	
	//Modelo de la tabla
	private static DefaultTableModel modeloTabla;
	
	//Constructor
	public TablaMemoria(){
		
		
		//Características del modelo de tabla empleado
		modeloTabla=new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Dirreción", "Proceso","M.Ocupada"
			}
		);
		
		this.setEnabled(false);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setDefaultEditor(Object.class,null);
		this.setModel(modeloTabla);
		
		//Llenar la tabla con las 32 posiciones
		filler();

	}
	

	//Metodo para colorear la tabla segun el proceso que tenga el indice
	@Override
	public Component prepareRenderer (TableCellRenderer renderer, int rowindex, int columindex) {
		
		//Se obtiene la celda
		Component componente = super.prepareRenderer(renderer, rowindex, columindex);
		
		//Si existe un valor(color) relacionado con algun proceso, la celda se colora de dicho color.
		componente.setBackground((Recursos.CellColor[rowindex]!=null)?Color.decode(Recursos.CellColor[rowindex]):Color.white);
		this.repaint();//Repintar la tabla para que no se vean anomalias
		
		return componente; 
		
	}	
	
	
	//Este metodo rellena con la informacion de procesos las casillas en la tabla que simula la memoria
	public static void RellenarMemoria() {
		
		//Recorrer los indices de la tabla
		for(int cont=0;cont<32;cont++) {
				
			//En la posicion dada rellenar la fila con la informacion del proceso (id y memoria ocupada en cierto bloque)
			modeloTabla.setValueAt( (String) ( (Recursos.Memoria[cont]!=-1)?Integer.toString(Recursos.Memoria[cont]):""), cont, 1);
			modeloTabla.setValueAt( (String) ( (Recursos.Asignaciones[cont]!=-1)?Integer.toString(Recursos.Asignaciones[cont]):""), cont, 2);
				
		}
		
		
	}
	
	
	
	//Rellenar la tabla incial
	public static void filler() {
		
		try {
			
			modeloTabla.setRowCount(0);
			
			//Crea las 32 posiciones de la memoria
			for(int cont=0;cont<32;cont++) {
				modeloTabla.addRow(new String[] {cont+"","",""});
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
}
