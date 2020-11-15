package principal;


import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.Proceso;
import planificador.Planificador;

//Clase Tabla para hacer posible la gestión de empleados
public class Tabla extends JTable{
	
	//Modelo de la tabla
	private static DefaultTableModel modeloTabla;
	
	//Constructor
	public Tabla(){
		
	
		//Características del modelo de tabla empleado
		modeloTabla=new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID","Estado", "T. llegada","P. Inicial ","P. Actual", "T. Procesador Requerido", "T. Procesador restante",
				"M. requerida", "Ubicación en Memoria", "Impresora Solicitada","Impresora Asignada"," Scaner Solicitado", 
				"Scaner Asignado", "Modem Solicitado", "Modem Asignado", "CD Solicitado", "CD Asignado"
			}
		);
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setDefaultEditor(Object.class,null);
		this.setModel(modeloTabla);


	}
	

	//
	public static void MostrarProcesos() {
		
		modeloTabla.setRowCount(0);
		
		//Mostrar proceso en ejecucion y suspendido
		if(Planificador.ejecucion!=null) {
			modeloTabla.addRow(ObtenerDatos(Planificador.ejecucion));
		}
		
		if(Planificador.expulsado!=null) {
			modeloTabla.addRow(ObtenerDatos(Planificador.ejecucion));
		}
		
		//Cola de prioridad de tiempo real
		Planificador.TiempoReal.setRecorrer();
		while(Planificador.TiempoReal.existe()) {
			
			Proceso leer=null;
			leer=Planificador.TiempoReal.obtenerProceso();
			
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));
			}
			
			Planificador.TiempoReal.avanzar();
		}
		
		//Cola de Prioridad 1 Usuario
		Planificador.SegundaPrioridad.setRecorrer();
		while(Planificador.SegundaPrioridad.existe()) {
			
			Proceso leer=null;
			leer=Planificador.SegundaPrioridad.obtenerProceso();
			
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));
			}
			
			Planificador.SegundaPrioridad.avanzar();
		}
		
		//Cola de Prioridad 2 Usuario
		
		Planificador.TerceraPrioridad.setRecorrer();
		while(Planificador.TerceraPrioridad.existe()) {
			
			Proceso leer=null;
			leer=Planificador.TerceraPrioridad.obtenerProceso();
			
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));
			}
			
			Planificador.TerceraPrioridad.avanzar();
		}
		
		//Cola de Prioridad 3 Usuario
		Planificador.CuartaPrioridad.setRecorrer();
		while(Planificador.CuartaPrioridad.existe()) {
			
			Proceso leer=null;
			leer=Planificador.CuartaPrioridad.obtenerProceso();
			
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));
			}
			
			Planificador.CuartaPrioridad.avanzar();
		}
		
		
		//Cola de llegada
		Planificador.ColaInicial.setRecorrer();
		while(Planificador.ColaInicial.existe()) {
			
			Proceso leer=null;
			leer=Planificador.ColaInicial.obtenerProceso();
			
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));
			}
			
			Planificador.ColaInicial.avanzar();
		}
		
		//Cola de Procesos finalizados
		Planificador.TrabajosFinalizados.setRecorrer();
		while(Planificador.ColaInicial.existe()) {
			
			Proceso leer=null;
			leer=Planificador.TrabajosFinalizados.obtenerProceso();
			
			if(leer!=null) {
				modeloTabla.addRow(ObtenerDatos(leer));
			}
			
			Planificador.TrabajosFinalizados.avanzar();
		}
		
	}
	
	
	//
	public static String[] ObtenerDatos(Proceso proceso) {
		String current[]=new String[17];
		
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
		
		return current;
	}
	
}