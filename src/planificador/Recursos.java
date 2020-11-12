package planificador;

//Importaciones
import modelo.Proceso;

//
public class Recursos {

	//RECURSOS DEL SSOP	
	private static int Memoria[]= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private static int Asignaciones[]= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
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
			
		boolean leer=true;
			
		Planificador.ColaInicial.setRecorrer();
			
		while((!Planificador.ColaInicial.Vacia()) && (leer)) {
				
			Proceso proceso=null;
				
			if((proceso=Planificador.ColaInicial.obtenerProceso())!=null) {
					
					
				if(proceso.getPrioridadActual()==0) {
						
					if(MemoriaTiemporeal>=proceso.getMemoriaRequerida()) {
							
						int RMemo=0;
						int AMemo=proceso.getMemoriaRequerida();
							
						for(int cont=0;cont<2;cont++) {
							if((Memoria[cont]==-1) && (RMemo<proceso.getMemoriaRequerida())) {
								
									
									
								Memoria[cont]=proceso.getID();
								RMemo+=32;
								MemoriaTiemporeal-=32;
								
								if(RMemo>AMemo) {
									Asignaciones[cont]=(32-(RMemo-AMemo));
								}else {
									Asignaciones[cont]=32;
								}
									
							}
						}
							
						Planificador.TiempoReal.Escribir(Planificador.ColaInicial.Leer());
							
					}else {
						leer=false;
					}
						
				}else {
					Planificador.TrabajosUsuario.Escribir(Planificador.ColaInicial.Leer());
				}
					
			}
				
			Planificador.ColaInicial.setRecorrer();
				
				
		}

			
			
	}
		
		
		
	public static void ClasificarColausuario() {

		boolean Recorrido =true;
			
		Planificador.TrabajosUsuario.setRecorrer();
			
		while((!Planificador.TrabajosUsuario.Vacia()) && (Recorrido)) {
				
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
						
					proceso.setEstado(2); //canbia el estado del proseso a listo
						
						
						
					int RMemo=0;
					int AMemo=proceso.getMemoriaRequerida();
						
					for(int cont=2;cont<Memoria.length;cont++) {
						if((Memoria[cont]==-1) && (RMemo<proceso.getMemoriaRequerida())) {
								
								
							proceso.setUbicacionMemoria(proceso.getUbicacionMemoria() +"&"+cont);
							MemoriaUsuario-=32;
							Memoria[cont]=proceso.getID();
							RMemo+=32;
								
							if(RMemo>AMemo) {
								Asignaciones[cont]=(32-(RMemo-AMemo));
							}else {
								Asignaciones[cont]=32;
							}
								
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
						Planificador.SegundaPrioridad.Escribir(Planificador.TrabajosUsuario.Leer());
							
					}else if(proceso.getPrioridadActual()==2) {//prioridad 3
						Planificador.TerceraPrioridad.Escribir(Planificador.TrabajosUsuario.Leer());
					}else {//prioridad 4
						Planificador.CuartaPrioridad.Escribir(Planificador.TrabajosUsuario.Leer());
					}
							
				}else {
						
					Recorrido=false;//para la cola TrabajosUsuario hasta que se liberen los recursos
						
				}
					
			}
				
			Planificador.TrabajosUsuario.setRecorrer();
				
		}

			
	}
	
	//Liberar los recursos solicitados
	public static void LiberarRecursos(Proceso proceso) {
			
		if(proceso!=null) {
				
			if(proceso.getPrioridadActual()==0) {
					
				for (int i = 0; i < 2; i++) {//busca la ubicacion en memoria del proceso tiempo real y la libera
					if (proceso.getID()==Memoria[i]) {
							
						Memoria[i]=-1;
						MemoriaTiemporeal+=32;
						Asignaciones[i]=-1;
							
					}
				}
					
			}else {
					
				for (int i =2 ; i < Memoria.length; i++) {//busca la ubicacion en memoria del proceso usuario y la libera
						
					if (proceso.getID()==Memoria[i]) {
						Memoria[i]=-1;
						MemoriaUsuario+=32;
						Asignaciones[i]=-1;
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
