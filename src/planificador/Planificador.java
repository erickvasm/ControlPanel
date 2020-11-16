package planificador;

//Importaciones
import estructura_almacenamiento.Cola;
import manejador_archivo.Lector;
import modelo.Proceso;
import principal.Interfaz;

/*La clase Planificador realiza la simulacion del sistema operativo de pruebas mediante su estructuacion como
'Hilo', esta clase tiene acceso sobre todas las demas.*/
public class Planificador extends Thread{

	
	
	//Atributos de la Clase Planificador
	private int SegundosOperacion=0;//Tiempo que lleva el planificador ejecutandose
	public static int QUANTUM=1000;//Quantum de verificacion y ejecucion de los procesos, 1000ms=1s
	private Lector lector=new Lector();//Obtener el lector de archivos
	public static int SiguienteID=1;//Esta variable posibilita que los procesos que son transformados en la clase Lector tengan su propio ID
	
	
	
	//COLAS DE PROCESOS
		//Cola de llegada de los procesos
	public static Cola ColaInicial=new Cola();//Cola de llegada
		//>Cola de Tiempo Real
	public static Cola TiempoReal=new Cola();//Prioridad 0
		//Cola de trabajos de usuario
	public static Cola TrabajosUsuario=new Cola();//Cola de Procesos de usuario (1,3,4)
	public static Cola SegundaPrioridad=new Cola();//Prioridad 1 (usuario)
	public static Cola TerceraPrioridad=new Cola();//Prioridad 2 (usuario)
	public static Cola CuartaPrioridad=new Cola();//Prioridad 3 (usuario)
		//Cola de trabajos Finalizados
	public static Cola TrabajosFinalizados=new Cola();//Cola de trabajos finalizados
	
	
	
	//Referencia del proceso en ejecucion y el expulsado, usadas en los metodos Procesador() & ProcesadorB()
	public static Proceso ejecucion=null;//Proceso en estado de ejecucion
	public static Proceso expulsado=null;//Proceso en estado de expulsado
	
	
	
	//Constructor Planificador
	public Planificador() {
		//Vacio
	}
	
	
	
	//Estructuracion del planificador
	@Override
	public void run() {
		
		//Control de Excepciones
		try {
			
			
			/*Este metodo agrega en una lista los colores (32) que el simulador
			tendra disponibles para representar las relaciones de los procesos con la memoria*/
			Recursos.AgregarColor();
			
			
			/*Para poder obtener los procesos se intenta establecer el flujo de lectura
			haciendo uso del metodo EstablecerLector() de la clase Lector, para posteriormente
			al usar el metodo ObtenerProceso() en la estructura ciclica siguiente
			se extraigan los procesos del archivo de descripciones*/
			lector.EstablecerLector();
			
			//El Planificador se ejecutara Mientras (VERDADERO)
			while(true) {
				
				//Escribir el tiempo que lleva el planificador ejecutandose (CONSOLA)
				System.out.println("\n\nPlanificador segundo "+SegundosOperacion+":\n\n");
				
				//Entrada & Clasificacion de Colas
				ObtenerProceso();//Obtener procesos del archivo de descripciones haciendo uso del metodo ExtraerProceso() en la clase Lector
				Recursos.ClasificarColaInicial();//Se Clasifica la Cola Inicial
				Recursos.ClasificarColausuario();//Se Clasifica la Cola de Usuario
				
									//Administrar los procesos a ejecutarse
				
				/*Este metodo determina que proceso es el siguiente a ejcutarse logicamente tomando
				 * en cuenta las prioridades y estados de estos*/
				Procesador();
				
				/*Llama el metodo MasterGui() en la clase Interfaz el cual mostrara toda 
				 * la informacion relacionada con los procesos y los recursos del SSOP (GUI)*/
				Interfaz.masterGui();//

				
				Thread.currentThread().sleep(QUANTUM);//Quantum de Operatividad del planificador
				
				
				System.out.println("\t  ------------");//Indicar en consola que se completo el Quantum (CONSOLA)
				
				
				/*Restar 1 al tiempo de procesado restante al Proceso que estuvo ejecucion,
				 * degradar prioridada si el Proceso que estuvo en ejecucion era de Usuario*/
				ProcesadorB();
				
				
				//Aumentar el tiempo de ejecucion del planificador (CONSOLA)
				SegundosOperacion++;
			}
			
		} catch (Exception e) {
			//Excepcion VACIA
		}
	}
	
	
	
	
	
	
	
									//METODOS DE PLANIFICACION
	
	
	//Funciones antes del cuantum de operatividad
	public void Procesador() {
		//Verificar si existe un proceso en ejecucion
		if(ejecucion!=null) {
			//Si lo hay
			
			//Verificar si el proceso es de usuario y si existen procesos de tiempo real
			if((ejecucion.getPrioridadActual()!=0) && (!TiempoReal.Vacia())) {
				//Es de usuario y existe un proceso de tiempo real en cola
				
				CambiarEstado(5);//Cambiar el estado del proceso de usuario a 'Expulsado'
				MostrarProceso();//Mostrar la informacion del proceso en ejecucion
				
				expulsado=ejecucion;//Colocar el proceso expulsado en la referencia indicada
				ejecucion=null;//Liberar la referencia de ejecucion
				ejecucion=SiguienteProceso();//Obtener el proceso de Tiempo real
				CambiarEstado(1);//Cambiar el estado del proceso de tiempo real recien obtenido a 'Corriendo'
				MostrarProceso();//Mostrar la informacion del proceso en ejecucion
			
			}else {
				//No es un proceso de usuario o la cola de prioridad de tiempo real esta vacia
				CambiarEstado(1);//Cambiar estado a corriendo
				MostrarProceso();//Mostrar la informacion del proceso en ejecucion
			}
			
		}else {
			//No lo hay
			ejecucion=SiguienteProceso();//Buscar un proceso para ejecutar
			CambiarEstado(1);//Cambiar el estado del proceso a 'Corriendo'
			MostrarProceso();//Mostrar la informacion del proceso en ejecucion
			
		}
	}
	

	//Funciones despues del Quantum de operatividad
	public void ProcesadorB() {
		//Verificar que antes se haya obtenido un proceso
		if(ejecucion!=null) {
			ActualizarTiempoRestante();//Restarle un segundo al tiempo de ejecucion restante
			
			//Verificar si el Proceso completo su tiempo de Procesado
			if(ejecucion.getTiempoRestante()<=0){
				//Si se completo
			
				//Se finaliza el Proceso
				CambiarEstado(4);//Cambiar el estado del proceso a 'Finalizado'
				MostrarProceso();//Mostrar la informacion del proceso en ejecucion
				
				Recursos.LiberarRecursos(ejecucion);//Liberar los recursos que disponia el Proceso
				ejecucion=null;//Liberar la referencia de ejecucion
				
				//Verificar que existe un proceso expulsado para ejecutarlo
				if(expulsado!=null) {
					//Existe un proceso expulsado
					
					ejecucion=expulsado;//Pasar el proceso a la referencia de ejecucion
					CambiarEstado(1);//Cambiar el estado del proceso a 'Corriendo'
					MostrarProceso();//Mostrar la informacion del proceso en ejecucion
					
					expulsado=null;//Vaciar la referencia de expulsado para las siguientes ejecuciones
				}
				
				
				
			//Si no se completo se verifica si es un Proceso de Usuario
			}else if(ejecucion.getPrioridadActual()!=0) {
				//El Proceso es de usuario por lo tanto se degrada
				CambiarEstado(2);//Se cambia el estado del proceso a 'Listo'
				DegradarPrioridad(ejecucion);//Se degrada la prioridad del proceso de usuario
				MostrarProceso();//Mostrar la informacion del proceso en ejecucion
				
				ejecucion=null;//Se Libera la referencia del proceso en ejecucion
				
				ejecucion=SiguienteProceso();//Obtener el siguiente Proceso
				MostrarProceso();//Mostrar la informacion del proceso en ejecucion
				
			}else {
				MostrarProceso();//Mostrar la informacion del proceso en ejecucion
			}
			
		}
	}
	
	
	
	
	//Cambiar el estado del proceso en Ejecucion
	public void CambiarEstado(int E) {
		//Si existe un proceso en ejecucion
		if(ejecucion!=null) {
			ejecucion.setEstado(E);//Cambiar el estado del proceso en ejecucion
			//1-Ejecucion,2-Listo,3-Bloqueado,4-Finalizado,5-Expulsado
		}
	}
	
	
	//Actualizar el tiempo restante del proceso en Ejecucion
	public void ActualizarTiempoRestante() {
		//Si existe un proceso en ejecucion
		if(ejecucion!=null) {
			ejecucion.setTiempoRestante(ejecucion.getTiempoRestante()-1);//Restar un segundo al proceso en ejecucion
		}
	}
	
	//Mostrar la informacion del proceso
	public void MostrarProceso() {
		//Si existe un proceso en ejecucion
		if(ejecucion!=null) {
			//Imprimir la informacion del proceso
			System.out.println("\tID Proceso->"+ejecucion.getID()+"|Prioridad->"+ejecucion.getPrioridadActual()+
					"|Tiempo Restante->"+ejecucion.getTiempoRestante()+"|Tiempo Requerido->"+ejecucion.getTiempoRequerido()+"|Estado:"+
					((ejecucion.getEstado()==1)?"Corriendo":((ejecucion.getEstado()==2)?"Listo":((ejecucion.getEstado()==3)?"Bloqueado":((ejecucion.getEstado()==4)?"Finalizado":"Suspendido")))));
		}
	}
	
	
	
	
	//Este metodo degrada la prioridad del proceso y lo manda a cola definida por su nueva prioridad
	public void DegradarPrioridad(Proceso proceso) {
		//Verificar que el proceso a degradar no este vacio
		if(proceso!=null) {
				
			//Confirmar si se puede degradar una prioridad mas baja
			if(proceso.getPrioridadActual()!=3) {
				//Degradar el atributo de PrioridadActual del proceso
				proceso.setPrioridadActual(proceso.getPrioridadActual()+1);
			}
				
			//Enviar el proceso a la cola segun su prioridad actual
			switch(proceso.getPrioridadActual()) {
				
				//Prioridad de 2 cola de 'TerceraPrioridad'
				case 2:{
					TerceraPrioridad.Escribir(proceso);//Mandar a la cola de prioridad 2
				}break;
					
				//Prioridad de 3 cola de 'CuartaPrioridad'
				case 3:{
					CuartaPrioridad.Escribir(proceso);//Mandar a la cola de prioridad 3
				}break;
			}
		}
	}
	
	
	
	
	//Obtener el siguiente proceso a ejecutarse segun su prioridad
	public Proceso SiguienteProceso() {
			
		Proceso proceso=null;//Referencia del proximo proceso a ejecutar
			
		//Se revisa la prioridad de procesos de tiempo real Prioridad (0)
		if(!TiempoReal.Vacia()) {
			//Si hay procesos
			proceso=TiempoReal.Leer();//El siguiente proceso a ejecutar sera de prioridad (0) 'De tiempo Real'
				
		//Se revisa la prioridad 1 de trabajos de usuario
		}else if(!SegundaPrioridad.Vacia()) {
			//Si hay procesos
			proceso=SegundaPrioridad.Leer();//El siguiente proceso a ejecutar sera de prioridad (1) 'De Usuario'
				
		//Se revisa la prioridad 2 de trabajos de usuario
		}else if(!TerceraPrioridad.Vacia()) {
			//Si hay procesos
			proceso=TerceraPrioridad.Leer();//El siguiente proceso a ejecutar sera de prioridad (2) 'De Usuario'
				
		//Se revisa la prioridad 3 de trabajos de usuario
		}else if(!CuartaPrioridad.Vacia()){
			//Si hay procesos
			proceso=CuartaPrioridad.Leer();//El siguiente proceso a ejecutar sera de prioridad (3) 'De Usuario'
		}
			
		return proceso;//Se retorna el siguiente proceso a ejecutar
	}
	
					
	
							//LECTOR DE PROCESOS
	
	
	//Este metodo utiliza la clase Lector para obtener objetos Proceso con los datos presentes
	//en el archivo de descripciones
	public void ObtenerProceso() {
		//Verficar si existe un flujo con el archivo
		if(lector.FlujoEstablecido()) {
			
			Proceso extraido=null;//Referencia donde se guardara el proceso
			//Haciendo uso del metodo de la clase Lector 'ExtraerProceso'
			//verificamos si el objeto obtenido no esta vacio
			if((extraido=lector.ExtraerProceso())!=null) {
				//El objeto no es vacio se envia a la cola de trabajo inicial
				ColaInicial.Escribir(extraido);
			}
		}
	}
	
	
	
	
	
}