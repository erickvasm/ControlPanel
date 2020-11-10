package planificador;

//Importaciones
import modelo.Proceso;

//
public class Recursos {

	//RECURSOS DEL SSOP	
	private static int Memoria[]= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private static int MemoriaTiemporeal=64;
	private static int MemoriaUsuario=960;
	
	private static int Impresoras=2;
	private static int Escaner=1;
	private static int Modem=1;
	private static int CD=2;
	
	
	//
	public Recursos() {
		//
	}
	
	
	
	
	
	
	//Asignar los recursos a los procesos de tiempo real y mueve los procesos de usuario a la cola TrabajosUsuario
	public static void ClasificarColaInicial() {
		
		Planificador.ColaInicial.setRecorrer();
		
		while(Planificador.ColaInicial.existe()) {
			
			Proceso proceso=null;
			

			if((proceso=Planificador.ColaInicial.Leer())!=null) {

				if((proceso=Planificador.ColaInicial.obtenerProceso())!=null) {
					
	
					if (proceso.getPrioridadActual()==0) {//valida si es de priorida 0
						
						if (MemoriaTiemporeal>=proceso.getMemoriaRequerida()) {//valida si hay memoria para el proceso de tiemporeal
							System.out.println("Hay memoria "+MemoriaTiemporeal);
							Planificador.ColaInicial.Leer();
							Double calculo =  (double) proceso.getMemoriaRequerida()/32;
							//System.out.println("memoria requerida "+proceso.getMemoriaRequerida()+"proridad "+proceso.getPrioridadActual());
							//System.out.println(Math.ceil(calculo));
							Double bloques =Math.ceil(calculo);
							for (int i = 0; i < 2; i++) {
								
								if ((Memoria[i]==-1) && (bloques>0)) {
									
									Memoria[i]=proceso.getID();//ubica al proseso en un bloque de memoria 
									MemoriaTiemporeal-=32;//asigna memoria al proceso de tiempo real
									proceso.setUbicacionMemoria(proceso.getUbicacionMemoria() + i);
									
									bloques--;
								}
							}
							Planificador.TiempoReal.Escribir(proceso);
						}else {
							System.out.println("No hay memoria "+MemoriaTiemporeal);
							return;
						}
					
					}else {
						//System.out.println("ID de menor prioridad"+proceso.getID()+"\n");
						Planificador.TrabajosUsuario.Escribir(proceso);//inserta el proceso a la cola TrabajosUsuario
						Planificador.ColaInicial.Leer();
					}
				}
			
			Planificador.ColaInicial.avanzar(); //Avanza la siguiente proceso
			
			}
		}

		
		
	}
	
	public static void ClasificarColausuario() {

		Planificador.TrabajosUsuario.setRecorrer();
		boolean Recorrido =true;
		while((Planificador.TrabajosUsuario.existe())&& Recorrido) {
			
			Proceso proceso=null;
			
			if((proceso=Planificador.TrabajosUsuario.obtenerProceso())!=null) {
				
				boolean recusos[]= {false,false,false,false,false};// guarda las validaciones de os recursos 
				if (MemoriaUsuario>=proceso.getMemoriaRequerida()) {//si hay memoria suficiente
					
					recusos[0]=true;
					
				}
				if (Impresoras>=proceso.getImpresorasSolicitas()) {//si hay suficientes Impresoras
					
					recusos[1]=true;
					
				}
				
				if (CD>=proceso.getCDSolicitados()) {//si hay suficientes  unidades de CD
					
					recusos[2]=true;
					
				}
				
				if (Escaner>=proceso.getEscaneresSolicitados()) {//si hay suficientes Escaner
					
					recusos[3]=true;
					
				}
				
				if (Modem>=proceso.getModemSolicitado()) {//si hay suficientes Modem
					
					recusos[4]=true;
					
				}
				
				if ((recusos[0]) && (recusos[1]) && (recusos[2]) && (recusos[3]) && (recusos[4])) {//valida si hay ecursos para que puedan ejecutarse
					proceso.setEstado(2); //canbia el estao del proseso a listo
					Planificador.TrabajosUsuario.Leer();
						for (int i = 2; i < Memoria.length; i++) {//valida si hay memoria para el proceso de usuario
							Double calculo =  (double) proceso.getMemoriaRequerida()/32;
							//System.out.println("memoria requerida "+proceso.getMemoriaRequerida()+"proridad "+proceso.getPrioridadActual());
							//System.out.println(Math.ceil(calculo));
							Double bloques =Math.ceil(calculo);	
							if (Memoria[i]==-1&&bloques>0) {
									//System.out.println("ID"+proceso.getID()+"/n");
									Memoria[i]=proceso.getID();//ubica al proseso en un bloque de memoria 
									MemoriaUsuario-=32;//asigna memoria al proceso de MemoriaUsuario

									proceso.setUbicacionMemoria(proceso.getUbicacionMemoria() + String.valueOf(i));
									
									
									bloques--;
									}
					 
						}
						//asigna los demar recursos
						Escaner-=proceso.getEscaneresSolicitados();
						proceso.setEscaneresAsignados(proceso.getEscaneresSolicitados());
						
						Impresoras-=proceso.getImpresorasSolicitas();
						proceso.setImpresorasAsignadas(proceso.getImpresorasSolicitas());
						
						CD-=proceso.getCDSolicitados();
						proceso.setCDAsignados(proceso.getCDSolicitados());
						
						Modem-=proceso.getModemSolicitado();
						proceso.setModemAsignadas(proceso.getModemSolicitado());
						//mueve al proceso a si respetiva cola de prioridad 
						if (proceso.getPrioridadActual()==1) {//prioridad 2
							Planificador.SegundaPrioridad.Escribir(proceso);
							
						}else if(proceso.getPrioridadActual()==2) {//prioridad 3
							Planificador.TerceraPrioridad.Escribir(proceso);
						}else {//prioridad 4
							Planificador.CuartaPrioridad.Escribir(proceso);
						}
						
				}else {
					
					Recorrido=false;//para la cola TrabajosUsuario hasta que se liberen los recursos
					
				}
				
			}
			
			Planificador.TrabajosUsuario.avanzar();
			
		}

		
	}
	
	//Liberar los recursos solicitados
	static void LiberarRecursos(Proceso proceso) {
		if(proceso!=null) {
			
			if(proceso.getPrioridadActual()==1) {
				for (int i = 0; i < 2; i++) {//busca la ubicacion en memoria del proceso tiempo real y la libera
					if (proceso.getID()==Memoria[i]) {
						
						Memoria[i]=-1;
						MemoriaTiemporeal+=32;
					}
				}
			}else {
				for (int i =2 ; i < Memoria.length; i++) {//busca la ubicacion en memoria del proceso usuario y la libera
					if (proceso.getID()==Memoria[i]) {
						
						Memoria[i]=-1;
						MemoriaUsuario+=32;
					}
					}
				//libera los recursos Asignados
				Escaner+=proceso.getEscaneresAsignados();
				Impresoras+=proceso.getImpresorasAsignadas();
				CD+=proceso.getCDAsignados();
				Modem+=proceso.getModemSolicitado();
			}
		
		}
	}


}
