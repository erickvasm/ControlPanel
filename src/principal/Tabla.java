package principal;

//Importaciones
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import modelo.Proceso;
import planificador.Planificador;
import planificador.Recursos;

//Esta clase permite visualizar las descripciones de los distintos procesos durante la simulacion del SSOP
public class Tabla extends JTable{
	
	//Modelo de la tabla
	private static DefaultTableModel modeloTabla;
	
	//Constructor
	public Tabla(){
		
	
		//Características del modelo de Tabla de procesos
		modeloTabla=new DefaultTableModel(
			
			//Sin valores por defecto
			new Object[][] {
			},
			
			//Se crean las columnas para las distintas descripciones de los Procesos
			new String[] {
				"ID","Estado", "T. llegada","P. Inicial ","P. Actual", "T. Procesador Requerido", "T. Procesador restante",
				"M. requerida", "Ubicación en Memoria", "Impresora Solicitada","Impresora Asignada"," Scaner Solicitado", 
				"Scaner Asignado", "Modem Solicitado", "Modem Asignado", "CD Solicitado", "CD Asignado"
			}
		);
		
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//Tabla no editable
		this.setDefaultEditor(Object.class,null);
		this.setModel(modeloTabla);//Establecer el modelo de la tabla


	}
	
	
	//Metodo para colorear las filas de la tabla segun el proceso en el indice recorrido
	@Override
	public Component prepareRenderer (TableCellRenderer renderer, int rowindex, int columindex) {
			
		//referencia de celda actual
		Component componente=null;
		
		
		//Control de excepciones
		try {
			
			//Se obtiene la celda actual
			componente = super.prepareRenderer(renderer, rowindex, columindex);
			
			//Estas variables definiran si el la fila actual debera colorearse
			boolean condition=true;
			int currentId=-1;
			
			//Obtenemos el id del proceso en la fila recorrida por el metodo
			currentId=Integer.parseInt((String) modeloTabla.getValueAt(rowindex, 0));
			
			//Evaluamos si ese Id de proceso tiene direcciones en memoria
			for(int cont=0;cont<Recursos.Memoria.length;cont++) {
				//Verificar si en la Memoria de la clase 'Recursos' existe el Id anteriormente obtenido
				if((condition) && (Recursos.Memoria[cont]==currentId)) {
					//Se encuentra una direcion en Memoria relacionada con el Id del proceso
					condition=false;
					currentId=cont;
				}
			}		
			
			//Si se encontro una direccion de memoria relacionada al proceso
			if(!condition) {
				//Si existe un valor(color HEXA) relacionado con el proceso, la celda se colora de dicho color.
				componente.setBackground((Recursos.CellColor[currentId]!=null)?Color.decode(Recursos.CellColor[currentId]):Color.white);
				this.repaint();//Repintar la tabla para que no se vean anomalias visuales
				
			}else {
				//Si el proceso no tiene relacion con la memoria
				componente.setBackground(Color.white);//La fila en la cual se encuentran las descripciones de ese proceso se torna de color blanca
				this.repaint();//Repintar la tabla para que no se vean anomalias visuales
			}
		
		
		} catch (Exception e) {
			//Excepcion vacia
		}
		
		return componente; 
			
	}	

		
	/*Este metodo recorre todas las filas que se encuentran en 
	la clase 'Planificador' mostrando todos los procesos con sus respectivas descripciones*/
	public static void MostrarProcesos() {
		
		modeloTabla.setRowCount(0);//Vaciar la tabla
		
							
							//PROCESO EN EJECUCION Y EXPULSADO
		
		//Mostrar proceso en ejecucion si existe
		if(Planificador.ejecucion!=null) {
			modeloTabla.addRow(ObtenerDatos(Planificador.ejecucion));
		}
		
		//Mostrar el proceso expulsado si existe
		if(Planificador.expulsado!=null) {
			modeloTabla.addRow(ObtenerDatos(Planificador.ejecucion));
		}
		
		
							//COLAS DE PROCESOS
		
		//Mostrar los procesos en la cola de tiempo real
		Planificador.TiempoReal.setRecorrer();//Reiniciar el recorrido
		//Mientras la referencia de recorrido no este vacia
		while(Planificador.TiempoReal.existe()) {
			
			Proceso leer=null;//Referencia del proceso a leer
			leer=Planificador.TiempoReal.obtenerProceso();//Leer el proceso sin desligarlo de la cola perteneciente
			
			//Si el proceso no esta vacio
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));//Se agrega una fila en la tabla con las descripciones del proceso provistas por el metodo 'ObtenerProcesos()'
			}
			
			Planificador.TiempoReal.avanzar();//Se avanza en el recorrido de la cola
		}
		
		
		//Mostrar la Cola de Prioridad 1 (Usuario)
		Planificador.SegundaPrioridad.setRecorrer();//Reiniciar el recorrido
		//Mientras la referencia de recorrido no este vacia
		while(Planificador.SegundaPrioridad.existe()) {
			
			Proceso leer=null;//Referencia del proceso a leer
			leer=Planificador.SegundaPrioridad.obtenerProceso();//Leer el proceso sin desligarlo de la cola perteneciente
			
			//Si el proceso no esta vacio
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));//Se agrega una fila en la tabla con las descripciones del proceso provistas por el metodo 'ObtenerProcesos()'
			}
			
			Planificador.SegundaPrioridad.avanzar();//Se avanza en el recorrido de la cola
		}
		
		
		
		
		
		
		//Mostrar la Cola de Prioridad 2 (Usuario)
		Planificador.TerceraPrioridad.setRecorrer();//Reiniciar el recorrido
		//Mientras la referencia de recorrido no este vacia
		while(Planificador.TerceraPrioridad.existe()) {
			
			Proceso leer=null;//Referencia del proceso a leer
			leer=Planificador.TerceraPrioridad.obtenerProceso();//Leer el proceso sin desligarlo de la cola perteneciente
		
			//Si el proceso no esta vacio
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));//Se agrega una fila en la tabla con las descripciones del proceso provistas por el metodo 'ObtenerProcesos()'
			}
			
			Planificador.TerceraPrioridad.avanzar();//Se avanza en el recorrido de la cola
		}
		
				
		
		
		//Mostrar la Cola de Prioridad 3 (Usuario)
		Planificador.CuartaPrioridad.setRecorrer();//Reiniciar el recorrido
		//Mientras la referencia de recorrido no este vacia
		while(Planificador.CuartaPrioridad.existe()) {
			
			Proceso leer=null;//Referencia del proceso a leer
			leer=Planificador.CuartaPrioridad.obtenerProceso();//Leer el proceso sin desligarlo de la cola perteneciente
			
			//Si el proceso no esta vacio
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));//Se agrega una fila en la tabla con las descripciones del proceso provistas por el metodo 'ObtenerProcesos()'
			}
			
			Planificador.CuartaPrioridad.avanzar();//Se avanza en el recorrido de la cola
		}
		
		
		
	
		
		//Mostrar la Cola de trabajos de usuario
		Planificador.TrabajosUsuario.setRecorrer();//Reiniciar el recorrido
		//Mientras la referencia de recorrido no este vacia
		while(Planificador.TrabajosUsuario.existe()) {
							
			Proceso leer=null;//Referencia del proceso a leer
			leer=Planificador.TrabajosUsuario.obtenerProceso();//Leer el proceso sin desligarlo de la cola perteneciente
							
			//Si el proceso no esta vacio
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));//Se agrega una fila en la tabla con las descripciones del proceso provistas por el metodo 'ObtenerProcesos()'
			}
							
			Planificador.TrabajosUsuario.avanzar();//Se avanza en el recorrido de la cola
		}
		
				
		
		
		
		//Mostrar la Cola de Llegada de los Procesos
		Planificador.ColaInicial.setRecorrer();//Reiniciar el recorrido
		//Mientras la referencia de recorrido no este vacia
		while(Planificador.ColaInicial.existe()) {
			
			Proceso leer=null;//Referencia del proceso a leer
			leer=Planificador.ColaInicial.obtenerProceso();//Leer el proceso sin desligarlo de la cola perteneciente
			
			//Si el proceso no esta vacio
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));//Se agrega una fila en la tabla con las descripciones del proceso provistas por el metodo 'ObtenerProcesos()'
			}
			
			Planificador.ColaInicial.avanzar();//Se avanza en el recorrido de la cola
		}
		
		
		
		//Mostrar la Cola de Procesos finalizados
		Planificador.TrabajosFinalizados.setRecorrer();//Reiniciar el recorrido
		//Mientras la referencia de recorrido no este vacia
		while(Planificador.TrabajosFinalizados.existe()) {
			
			Proceso leer=null;//Referencia del proceso a leer
			leer=Planificador.TrabajosFinalizados.obtenerProceso();//Leer el proceso sin desligarlo de la cola perteneciente
			
			//Si el proceso no esta vacio
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));//Se agrega una fila en la tabla con las descripciones del proceso provistas por el metodo 'ObtenerProcesos()'
			}
			
			Planificador.TrabajosFinalizados.avanzar();//Se avanza en el recorrido de la cola
		}
		
	}
	
	
	
	
	/*Este metodo obtiene las propiedades del proceso y los convierte en formato de 
	de arreglo del tipo String para una mejor manipulacion a la hora de agregarlos a la tabla de Procesos*/
	public static String[] ObtenerDatos(Proceso proceso) {
		
		String current[]=new String[17];//Arreglo String con las descripciones de los procesos
		
		//Agregar las descripciones de los proceso al Arreglo String
		current[0]=proceso.getID()+"";
		current[1]=((proceso.getEstado()==1)?"Ejecucion":((proceso.getEstado()==2)?"Listo":((proceso.getEstado()==3)?"Bloqueado":((proceso.getEstado()==4)?"Finalizado":"Listo"))));
		current[2]=proceso.getTiempoLlegada()+"";
		current[3]=proceso.getPrioridadInicial()+"";
		current[4]=proceso.getPrioridadActual()+"";
		current[5]=proceso.getTiempoRequerido()+"";
		current[6]=proceso.getTiempoRestante()+"";
		current[7]=proceso.getMemoriaRequerida()+"";
		current[8]=proceso.getUbicacionMemoria()+"";
		current[9]=proceso.getImpresorasSolicitas()+"";
		current[10]=proceso.getImpresorasAsignadas()+"";
		current[11]=proceso.getEscaneresSolicitados()+"";
		current[12]=proceso.getEscaneresAsignados()+"";
		current[13]=proceso.getModemSolicitado()+"";
		current[14]=proceso.getModemAsignadas()+"";
		current[15]=proceso.getCDSolicitados()+"";
		current[16]=proceso.getCDAsignados()+"";	
		
		return current;//Devolver el arreglo con las descripciones de los procesos
	}
	
}