package planificador;

//Importaciones
import modelo.Proceso;
import java.util.ArrayList;

//Clase Recursos, utilidad de administracion de recursos (Memoria, Disp.Entrada/Salida)
public class Recursos {

								//RECURSOS DEL SSOP
	
	
	
	//RECURSO DE MEMORIA
	
	/*El siguiente arreglo define que bloques de la memoria se le han asignado a los procesos,
	 inicializado en -1 por el hecho de que ningun proceso esta ocupando ninguno de los 32 bloques*/
	public static int Memoria[]= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	
	/*El siguiente arreglo define la cantidad de memoria utilizada en cada uno de los bloques de los 32 existentes
	,inicializado en -1 por default*/
	public static int Asignaciones[]= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	
	public static String CellColor[]=new String[32];//Color en valor hexadecimal de los bloques de memoria
	public static int MemoriaTiemporeal=64;//Memoria de tiempo real disponible
	public static int MemoriaUsuario=960;//Memoria de Usuario disponible
	
	//Arreglo donde almacenara los 32 colores que relacionan la Memoria con los procesos
	private static ArrayList<String> colores = new ArrayList<String>(); 
	
	
	//Recursos del SSOP
	public static int Impresoras=2;//Impresoras disponibles
	public static int Escaner=1;//Escaneres disponibles
	public static int Modem=1;//Modem disponibles
	public static int CD=2;//CD disponibles
	
	
	//Constructor Recursos
	public Recursos() {
		//Se agregan los colores para poder usarlos de distintivos en los bloques de la memoria
		AgregarColor();
	}
	 
	//Este metódo agrega los colores en "HEXADECIMAL" a la lista de 'colores' que es un ArrayList 
	public static void AgregarColor() {
		
		//32 Colores en HEXADECIMAL
		colores.add("#F0F8FF"); 
		colores.add("#FAEBD7");
		colores.add("#00FFFF");
		colores.add("#7FFFD4");
		colores.add("#0000FF");
		colores.add("#8A2BE2");
		colores.add("#A52A2A");
		colores.add("#DEB887");
		colores.add("#5F9EA0");
		colores.add("#7FFF00");
		colores.add("#D2691E");
		colores.add("#FF7F50");
		colores.add("#6495ED");
		colores.add("#DC143C");
		colores.add("#00FFFF");
		colores.add("#00008B");
		colores.add("#008B8B");
		colores.add("#B8860B");
		colores.add("#A9A9A9");
		colores.add("#006400");
		colores.add("#A9A9A9");
		colores.add("#BDB76B");
		colores.add("#8B008B");
		colores.add("#556B2F");
		colores.add("#FF8C00");
		colores.add("#9932CC");
		colores.add("#8B0000");
		colores.add("#E9967A");
		colores.add("#8FBC8F");
		colores.add("#483D8B");
		colores.add("#2F4F4F");
		colores.add("#00CED1");
		
	}
	
	
	/*Recorrer la cola inicial, asignar memoria si el proceso es de prioridad de tiempo real,
	 si se trata de un proceso de Usuario se envia a la cola de 'TrabajosUsuario'*/
	public static void ClasificarColaInicial() {
			
		boolean Leer=true; //Bandera booleana, su valor cambia a falso cuando a un proceso de tiempo real no se le pudo asignar memoria
			
		Planificador.ColaInicial.setRecorrer(); //Establecer el recorrido de la cola de inicio
		// Mientras la Cola Inicial no este vacia y la variable Leer sea verdadera 
		while((!Planificador.ColaInicial.Vacia()) && (Leer)) { 	
			
			Proceso proceso=null;//Referencia del proceso a leer
			
			//Si al leer el proceso de la Cola Inicial, este no esta vacio
			if((proceso=Planificador.ColaInicial.obtenerProceso())!=null) { 
				//El proceso se lee de la Cola Inicial
				
				//Se verifica si la prioridad de ese proceso leido es de tiempo real
				if(proceso.getPrioridadActual()==0) {
					//El Proceso es de tiempo real	
					
					//Verificamos si existe memoria de tiempo real libre para ese proceso
					if(MemoriaTiemporeal>=proceso.getMemoriaRequerida()) { 
						//Hay memoria de tiempo real disponible
						
						proceso.setEstado(2);//Se cambia el estado del proceso a listo
						
						/*Verificar si existe colores disponibles para relacionar este proceso
						con los bloques se le asignaran mas adelante*/
						if(colores.size()!=0){
							proceso.setColorMemo(colores.get(0));//Se le asigna un color distintivo desde el ArrayList 'colores'
							colores.remove(0);//El color antes asignado se eliminar del ArrayList 'colores' para que ningun otro proceso haga uso este.
						}
						
						int RMemo=0;//Memoria asignada al proceso
						int AMemo=proceso.getMemoriaRequerida();//Memoria requerida por el proceso
							
						//Se recorren los primeros dos bloques en la memoria 'Memoria' los cuales estan designados para procesos de tiempo real
						for(int cont=0;cont<2;cont++) {
						
							/*Se verifica si el bloque en el indice 'cont' esta desocupado
							y si el proceso aun requiere de mas bloques de memoria*/
							if((Memoria[cont]==-1) && (RMemo<proceso.getMemoriaRequerida())) {
								
								//Se agrega la ubicacion 'cont' de la memoria al proceso actual
								proceso.setUbicacionMemoria((proceso.getUbicacionMemoria().length()==0)?(cont+""):(proceso.getUbicacionMemoria() +","+cont));
								
								/*Para relacionar graficamente el proceso con la memoria se obtiene el color distintivo del proceso y mediante
								el arreglo 'CellColor' se indica que el bloque 'cont' de la memoria tiene ese color*/
								CellColor[cont]=proceso.getColorMemo();
								
								Memoria[cont]=proceso.getID();//Asignamos el ID del proceso al bloque 'cont' de la memoria
								MemoriaTiemporeal-=32;//Se resta un bloque (32) a la memoria de tiempo real disponible
								RMemo+=32;//Se suma un bloque de (32) de memoria asignada al proceso
								
								
								//Se define cuanta memoria se ocupa por cada bloque
								//Se comprueba si la memoria asignada al proceso es mayor a la memoria requerida por dicho procesos
								if(RMemo>AMemo) { 
									//Si es asi en el arreglo de 'Asignaciones' indice 'cont' tendra el valor resultante de restar 32
									//menos la resta de la memoria asignada al proceso menos la memoria requeridad por el proceso 
									Asignaciones[cont]=(32-(RMemo-AMemo)); 
								}else {
									//El arreglo de 'Asignaciones' en el indice 'cont' tendra el valor de '32'
									Asignaciones[cont]=32;
								}
									
							}
						}
						
						/*Al terminar de asignar los recursos al proceso de tiempo real se envia a la cola de procesos de tiempo real
						para su posterior ejecucion*/
						Planificador.TiempoReal.Escribir(Planificador.ColaInicial.Leer());
							
					}else {
						//Si no hay suficiente memoria para el proceso de tiempo real leido, se detiene el recorrido de la Cola Inicial
						Leer=false; 
					}
						
				}else {
					//Si el proceso es de prioridad de Usuario se envia a la cola de trabajos de usuario
					Planificador.TrabajosUsuario.Escribir(Planificador.ColaInicial.Leer());
				}
					
			}
				
			Planificador.ColaInicial.setRecorrer();//Se establece el recorrido nuevamente por si algun proceso se movio de la cola inicial a otra
				
		}
			
	}
		
		
	/*Recorrer la cola de trabajos de usuario, asignar memoria y recursos si se disponen,
	 los procesos se envian a las colas dependiendo de su prioridad (1,2,3)*/
	public static void ClasificarColausuario() {

		boolean Recorrido =true;//Bandera booleana, su valor cambia a falso cuando a un proceso de tiempo real no se le pudo asignar memoria
			
		Planificador.TrabajosUsuario.setRecorrer();//Establecer el recorrido de la cola de trabajos de usuario
			
		// Mientras la Cola de Trabajos de usuario no este vacia y la variable Recorrido sea verdadera 
		while((!Planificador.TrabajosUsuario.Vacia()) && (Recorrido)) {
				
			Proceso proceso=null;//Referencia del proceso a leer
			
			//Si al leer el proceso de la Cola de Trabajos de usuario, este no esta vacio
			if((proceso=Planificador.TrabajosUsuario.obtenerProceso())!=null) {
					
					
				boolean recusos[]= {false,false,false,false,false};//Guardar las validaciones de los recursos disponibles 
				
							//VERIFICAR RECURSOS DISPONIBLES PARA EVITAR INTERBLOQUEOS
				
				//si hay memoria de usuario suficiente
				if (MemoriaUsuario>=proceso.getMemoriaRequerida()) {
					recusos[0]=true;//Se confirma que existe memoria de usuario disponible para el proceso leido
				}
				
				//si hay suficientes Impresoras disponibles
				if (Impresoras>=proceso.getImpresorasSolicitas()) {
					recusos[1]=true;//Se confirma que existen impresoras disponibles para el proceso leido	
				}
					
				//si hay suficientes unidades de CD disponibles
				if (CD>=proceso.getCDSolicitados()) {
					recusos[2]=true;//Se confirma que existen unidades CD disponibles para el proceso leido		
				}
					
				//si hay suficientes Escaneres disponibles
				if (Escaner>=proceso.getEscaneresSolicitados()) {
					recusos[3]=true;//Se confirma que existen Escaneres disponibles para el proceso leido				
				}
					
				//si hay suficientes Modems disponibles
				if (Modem>=proceso.getModemSolicitado()) {
					recusos[4]=true;//Se confirma que existen Modems disponibles para el proceso leido
				}
					
				
				//Se verifica si todos los recursos que requiere el proceso estan disponibles
				if ((recusos[0]) && (recusos[1]) && (recusos[2]) && (recusos[3]) && (recusos[4])) {//valida si hay ecursos para que puedan ejecutarse
						
					proceso.setEstado(2); //Se cambia el estado del proceso a Listo
						
					/*Verificar si existe colores disponibles para relacionar este proceso
					con los bloques se le asignaran mas adelante*/
					if(colores.size()!=0){
						proceso.setColorMemo(colores.get(0));//Se le asigna un color distintivo desde el ArrayList 'colores'
						colores.remove(0);//El color antes asignado se elimina del ArrayList 'colores' para que ningun otro proceso haga uso este.
					}
						
						
					int RMemo=0;//Memoria asignada al proceso
					int AMemo=proceso.getMemoriaRequerida();//Memoria requerida por el proceso
						
					//Recorrer la memoria 'Memoria' desde el indice 3 hasta el 32
					for(int cont=2;cont<Memoria.length;cont++) {
						
						/*Se verifica si el bloque en el indice 'cont' esta desocupado
						y si el proceso aun requiere de mas bloques de memoria*/
						if((Memoria[cont]==-1) && (RMemo<proceso.getMemoriaRequerida())) {
							
							//Se agrega la ubicacion 'cont' de la memoria al proceso actual
							proceso.setUbicacionMemoria((proceso.getUbicacionMemoria().length()==0)?(cont+""):(proceso.getUbicacionMemoria() +","+cont));
							
							/*Para relacionar graficamente el proceso con la memoria se obtiene el color distintivo del proceso y mediante
							el arreglo 'CellColor' se indica que el bloque 'cont' de la memoria tiene ese color*/
							CellColor[cont]=proceso.getColorMemo();
							
							
							Memoria[cont]=proceso.getID();//Asignamos el ID del proceso al bloque 'cont' de la memoria
							MemoriaUsuario-=32;//Se resta un bloque (32) a la memoria de usuario disponible
							RMemo+=32;//Se suma un bloque de (32) de memoria asignada al proceso
									
							//Se define cuanta memoria se ocupa por cada bloque
							//Se comprueba si la memoria asignada al proceso es mayor a la memoria requerida por dicho procesos
							if(RMemo>AMemo) {
								//Si es asi en el arreglo de 'Asignaciones' indice 'cont' tendra el valor resultante de restar 32
								//menos la resta de la memoria asignada al proceso menos la memoria requeridad por el proceso 
								Asignaciones[cont]=(32-(RMemo-AMemo));
							}else {
								//El arreglo de 'Asignaciones' en el indice 'cont' tendra el valor de '32'
								Asignaciones[cont]=32;
							}
								
						}
					}
						
						
					//Asignar los recursos E/S solicitados por el proceso
					
						//Asinar escaneres
					Escaner-=proceso.getEscaneresSolicitados();
					proceso.setEscaneresAsignados(proceso.getEscaneresSolicitados());
						
						//Asignar impresoras
					Impresoras-=proceso.getImpresorasSolicitas();
					proceso.setImpresorasAsignadas(proceso.getImpresorasSolicitas());
							
						//Asignar unidades CD
					CD-=proceso.getCDSolicitados();
					proceso.setCDAsignados(proceso.getCDSolicitados());
							
						//Asignar modems
					Modem-=proceso.getModemSolicitado();
					proceso.setModemAsignadas(proceso.getModemSolicitado());
						
					//Mandar el proceso a la cola adecuada segun prioridad
					if (proceso.getPrioridadActual()==1) {
						//prioridad 1
						Planificador.SegundaPrioridad.Escribir(Planificador.TrabajosUsuario.Leer());	
					}else if(proceso.getPrioridadActual()==2) {
						//prioridad 2
						Planificador.TerceraPrioridad.Escribir(Planificador.TrabajosUsuario.Leer());
					}else {
						//prioridad 3
						Planificador.CuartaPrioridad.Escribir(Planificador.TrabajosUsuario.Leer());
					}
							
				}else {
					//El Proceso leido no puedo obtener todos los recursos que ocupaba
					
					Recorrido=false;//Detiene el recorrido de la cola de Trabajos de Usuarios hasta que se liberen los recursos
				}
					
			}
				
			Planificador.TrabajosUsuario.setRecorrer();//Se establece el recorrido nuevamente por si algun proceso se movio de la cola de trabajos de usuario a otra
				
		}

			
	}
	
	
	//Liberar los recursos asignados a los procesos
	public static void LiberarRecursos(Proceso proceso) {
			
		//Se comprueba que proceso no este vacio 
		if(proceso!=null) {
				
			//Si el proceso es de prioridad de tiempo real
			if(proceso.getPrioridadActual()==0) {
				
				colores.add(proceso.getColorMemo());//Regresar el color distintivo al ArrayList 'colores'
					
				//busca las ubicaciones en memoria asignadas al proceso tiempo real y las libera
				//Se recorre del indice 1 hasta 2
				for (int i = 0; i < 2; i++) {
					
					//Si el bloque de memoria con el indice 'i' posee el Id del proceso
					if (proceso.getID()==Memoria[i]) {
						
						MemoriaTiemporeal+=32;//Se suma un bloque disponible a la memoria de tiempo real
						Asignaciones[i]=-1;//Las asignaciones de cada bloque se resetean
						CellColor[i]=null;//Los colores que relacionan el proceso con memoria se resetean
						Memoria[i]=-1;//Se libera el bloque de memoria 'i'
						
					}
				}
				
			}else {
				//Si se trata de un proceso de usuario
				
				colores.add(proceso.getColorMemo());//Regresar el color distintivo al ArrayList 'colores'
				
				
				//busca las ubicaciones en memoria asignadas al proceso de usuario y las libera
				//Se recorre del indice 3 hasta 32	
				for (int i =2 ; i < Memoria.length; i++) {
					
					//Si el bloque de memoria con el indice 'i' posee el Id del proceso
					if (proceso.getID()==Memoria[i]) {
						
						MemoriaUsuario+=32;//Se suma un bloque disponible a la memoria de usuario
						Asignaciones[i]=-1;//Las asignaciones de cada bloque se resetean
						CellColor[i]=null;//Los colores que relacionan el proceso con memoria se resetean
						Memoria[i]=-1;//Se libera el bloque de memoria 'i'
						
					}
						
				}
					
				//Se liberan los recursos E/S asignados al proceso
				Escaner+=proceso.getEscaneresAsignados();
				Impresoras+=proceso.getImpresorasAsignadas();
				CD+=proceso.getCDAsignados();
				Modem+=proceso.getModemSolicitado();
					
			}
			
			Planificador.TrabajosFinalizados.Escribir(proceso);//Se manda el proceso al cola de proceso ya finalizados
		}
	}

	


}
