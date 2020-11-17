package principal;

//Importaciones
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import planificador.Recursos;

//Clase TablaMemoria muestra el estado de la memoria del SSOP
public class TablaMemoria extends JTable{
	
	//Modelo de la tabla
	private static DefaultTableModel modeloTabla;
	
	//Constructor
	public TablaMemoria(){
		
		
		//Características del modelo de Tabla Memoria
		modeloTabla=new DefaultTableModel(
			new Object[][] {
			},
			
			//Crear las columnas
			new String[] {
				"Dirreción", "Proceso","M.Ocupada"
			}
		);
		
		this.setEnabled(false);//No editable
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//No editable
		this.setDefaultEditor(Object.class,null);
		this.setModel(modeloTabla);//Establecer el modelo
		
		filler();//Crear las 32 filas que representan los bloques de memoria

	}
	

	/*Metodo para colorear las filas de la tabla si al relacionar el indice actual
	con el de Memoria 'Recursos' existe un metodo relacionado.*/
	@Override
	public Component prepareRenderer (TableCellRenderer renderer, int rowindex, int columindex) {
		
		//Referencia de celda actual
		Component componente = null;
		
		//Control de excepciones
		try {
			
			//Obtener celda actual
			componente = super.prepareRenderer(renderer, rowindex, columindex);
			//Si existe un valor(color HEXA) relacionado con algun proceso, la celda se colora de dicho color.
			componente.setBackground((Recursos.CellColor[rowindex]!=null)?Color.decode(Recursos.CellColor[rowindex]):Color.white);
			this.repaint();//Repintar la tabla para que no se vean anomalias
			
		} catch (Exception e) {
			//Excepcion VACIA
		}
		
		return componente; 
		
	}	
	
	
	//Este metodo rellena con la informacion de procesos las casillas en la tabla que simula la memoria
	public static void RellenarMemoria() {
		
		//Recorrer los indices de la tabla
		for(int cont=0;cont<32;cont++) {
				
			//En fila actual se muestra la informacion de los procesos (id y memoria ocupada en cierto bloque) si existe una relacion del indice actual y Memoria 'Recursos'
			modeloTabla.setValueAt( (String) ( (Recursos.Memoria[cont]!=-1)?Integer.toString(Recursos.Memoria[cont]):""), cont, 1);
			modeloTabla.setValueAt( (String) ( (Recursos.Asignaciones[cont]!=-1)?Integer.toString(Recursos.Asignaciones[cont]):""), cont, 2);
				
		}
		
	}
	
	
	
	//Crear las 32 filas de la tabla
	public static void filler() {
		
		//Control de Excepciones
		try {
			
			modeloTabla.setRowCount(0);//Resetear la tabla
			
			//Crea las 32 posiciones de la memoria
			for(int cont=0;cont<32;cont++) {
				modeloTabla.addRow(new String[] {cont+"","",""});//Se crea la fila numero 'cont'
			}
			
		} catch (Exception e) {
			//Excepcion VACIA
		}

	}
	
}
