package principal;

import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

//Clase Tabla para hacer posible la gestión de empleados
public class TablaMemoria extends JTable{
	
	//Modelo de la tabla
	private DefaultTableModel modeloTabla;
	
	//Constructor
	public TablaMemoria(){
		
	
		//Características del modelo de tabla empleado
		modeloTabla=new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Dirreción", "Proceso"
			}
		);
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setDefaultEditor(Object.class,null);
		this.setModel(modeloTabla);



	}
	

	
}
