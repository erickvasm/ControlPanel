package manejador_archivo;

//Importaciones
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import modelo.Proceso;
import planificador.Planificador;

/*Esta Clase mediante la otra denominada Conexion hacen posible obtener las lineas 
de descripcion de los procesos en el archivo*/
public class Lector {
	
	private Conexion con=new Conexion();//Se emplea esta clase para facilitar la obtencion del flujo de lectura del archivo
	private BufferedReader FlujoLector=null;//referencia del Flujo lector del archivo
	
	
	//Constructor Lector
	public Lector() {
		//VACIA
	}
	
	
	
	
	
	
	//Tratar de establecer el flujo de lectura
	public BufferedReader ObtenerFlujoLector() {
		//Definicion del archivo
		File archivo=null;
		archivo=con.ObtenerArchivo();//Se obtiene la referencia de la clase Conexion
		
		//Se verifica si el archivo no presenta anomalias
		if(archivo!=null) {
			
			//Se define el lector
			FileReader lector=null;
			lector=con.DefinirLector(archivo);//Se obtiene la referencia de la clase Conexion
			
			//Se verifica si el lector no presenta anomalias
			if(lector!=null) {
				
				//Se define el Flujo lector
				BufferedReader flujoLector=null;
				flujoLector=con.DefinirFlujoLector(lector);//Se obtiene la referencia de la clase Conexion
				
				//Se verifica si el flujo lector se definio correctamente
				if(flujoLector!=null) {
					return flujoLector;//Se definio correctamente, se retorna el flujo lector
				}else {
					return null;//Ocurrio un error al definir el flujo lector
				}
				
			}else {
				return null;//Ocurrio un error al definir el lector
			}
			
			
		}else {
			return null;//Existen anomalias en el archivo, no existe o es un directorio
		}
		
	}
	
	
	
	
	
	
	
	//Mediante el flujo lector establecido se lean las lineas del archivo que contiene la descripcion
	//de los proceso
	public Proceso ExtraerProceso() {
		
		//Proceso a devolverse
		Proceso extraido=null;
		
		//Verificar que la definicion del flujo de lectura sea exitosa
		if(FlujoEstablecido()) {
			
			//Control de excepciones
			try {
				
									//OBTENER LA CADENA DE CARACTERES
				
				String DescripcionProceso=null;//Almacena la cadena de caracteres de la linea en el archivo
				
				//Se verifica si la siguiente linea presenta caracteres
				if((DescripcionProceso=FlujoLector.readLine())!=null) {
					//Se define el valor de la variable de tipo Proceso,
					//al transformar las cadenas de caracteres antes obtenidas
					//mediante el metodo TransformarDescripcion()
					extraido=TransformarDescripcion(DescripcionProceso);
				}else {
					
					//Si el resultado es vacio se cierra las conexiones con el archivo
					
					con.CerrarLectura();//Cerrar conexiones de las referencias en la clase Conexion
					
					//Cerrar el flujo lector establecido
					//Control de excepciones
					try {
						FlujoLector.close();//Cerrar flujo con el archivo
					} catch (Exception e) {
						//Excepcion VACIA
					}
					
					FlujoLector=null;//La refencia se pone en nulo
					
				}
			} catch (Exception e) {
				//Excepcion VACIA
			}
					
		}
		
		return extraido;//Devolver el objeto Proceso 'extraido'
		
	}
	
	
	
	
	
	
	
	/*Transformar descripcion, recibe una secuencia de caracteres
	y mediante distintas condicional verifica que no presenten anomalias
	para luego realizar el traspaso de los datos brindados por la cadena
	de caracteres en nuevo objeto Proceso*/
	public Proceso TransformarDescripcion(String descripcion) {
		
		//Referencia al proceso que se albergara los datos de la cadena de caracteres
		Proceso transformado=null;
		
		//Verificar si la cadena de caracteres no es nula
		if(descripcion!=null) {
			
			//Control de excepciones
			try {
				
				String separar[]=null;//Almacena las descripciones del proceso separadas por ','
				separar=descripcion.split(",");//Se separan los datos
				
				//Se verifica que la separacion sea exitosa
				if(separar!=null) {
				
					//Se verifica si existen los 8 descripciones del proceso:
					//tiempo de llegada, prioridad, tiempo de procesado, memoria, cantidad impresoras,
					//cantidad escaneres, cantidad modems, cantidad de CD
					if(separar.length==8) {
						
						//Crear un objeto Proceso temporal
						Proceso temp=new Proceso();
						
						//Traspasar los datos separados al nuevo objeto
						temp.setTiempoLlegada(Integer.parseInt(separar[0]));
						temp.setPrioridadInicial(Integer.parseInt(separar[1]));
						temp.setTiempoRequerido(Integer.parseInt(separar[2]));
						temp.setMemoriaRequerida(Integer.parseInt(separar[3]));
						temp.setImpresorasSolicitas(Integer.parseInt(separar[4]));
						temp.setEscaneresSolicitados(Integer.parseInt(separar[5]));
						temp.setModemSolicitado(Integer.parseInt(separar[6]));
						temp.setCDSolicitados(Integer.parseInt(separar[7]));
						temp.setPrioridadActual(temp.getPrioridadInicial());
						temp.setTiempoRestante(temp.getTiempoRequerido());
						temp.setEstado(3);//Establecer estado en Bloqueado, Estados: 1=Corriendo, 2-Listo, 3-Bloquedo, 4-Finalizado, 5-Expulsado
						
						temp.setID(Planificador.SiguienteID);//Definir el id del proceso mediante la variable estatica SiguienteID
						
						Planificador.SiguienteID++;//Incrementar el valor de SiguienteID para proximos procesos
						
						//El objeto que retorna este metodo recibe el valor del objeto temporal creado
						//anteriormente
						transformado=temp;
					}
				}
			} catch (Exception e) {
				//Excepcion VACIA
			}
		}
		return transformado;//Retornar el objeto Proceso
	}
	
	
	
	
	
	/*Mediante el metodo ObtenerFlujo(), establecemos el flujo
	en la referencia FlujoLector*/
	public void EstablecerLector() {
		//La referencia toma el valor del flujo lector
		FlujoLector=ObtenerFlujoLector();
	}
	
	
	
	//Verificar si el flujo se encuentra establecido
	public boolean FlujoEstablecido() {
		return ((FlujoLector!=null)?true:false);//Si la refencia esta vacia significa no se encuentra establecio
	}
	
}
