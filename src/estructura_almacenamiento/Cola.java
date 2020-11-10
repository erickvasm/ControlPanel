package estructura_almacenamiento;

//Importes
import modelo.Proceso;

//Cola de los Proceso
public class Cola{

	//Referencias
	private Nodo primero;
	private Nodo ultimo;
	private Nodo recorrer;
	private int longitud;
			
			
	//Constructor
	public Cola() {
		recorrer=null;
		primero=null;
		ultimo=null;
		longitud=0;
	}
			
	
							//Recorrer la cola
	
	//Establecer Recorrido
	public void setRecorrer() {
		recorrer=primero;
	}
	
	
	//Existe el objeto en el recorrido
	public boolean existe() {
		return ((recorrer!=null)?true:false);
	}
	
	//Avanzar al siguiente nodo
	public void avanzar() {
		recorrer=recorrer.siguiente;
	}
	
	public Proceso obtenerProceso() {
		if(recorrer!=null) {
			return recorrer.dato;
		}else {
			return null;
		}
	}
	
	
	
									
							//Procesos
			
	//Insertar general
	public void Escribir(Proceso dato) {
		if(longitud!=0) {
			InsertarExistente(dato);
		}else {
			InsertarVacia(dato);
		}
	}
			
			
			
	//Insertar en una cola vacia
	public void InsertarVacia(Proceso dato) {
		Nodo nodoInsertar=new Nodo(dato);
		primero=nodoInsertar;
		ultimo=nodoInsertar;
		longitud++;
	}
			
			
	//Insertar en una cola ocupada
	public void InsertarExistente(Proceso dato) {
		Nodo nodoInsertar=new Nodo(dato);
		ultimo.siguiente=nodoInsertar;
		ultimo=nodoInsertar;
		longitud++;
	}
			
			
			
			
			
	//Leer general,usa los metodos LeerUnico y LeerExistente mediante condicionales
	public Proceso Leer() {
		Proceso dato=null;
		if(longitud==1) {
			dato= LeerUnico();
		}else {
			if(longitud!=0) {
				dato=LeerExistente();
			}
		}
				
		return dato;
	}
			
	//Leer con mas de un elemento de la cola
	public Proceso LeerExistente() {
		Nodo referencia;
		referencia=primero;
		primero=primero.siguiente;
		longitud--;
		return referencia.dato;
	}
			
			
	//Leer con un unico elemento en la cola
	public Proceso LeerUnico() {
		Nodo referencia;
		referencia=primero;
		primero=primero.siguiente;
		ultimo=null;
		longitud--;
		return referencia.dato;
	}
	
	//Obtener longitud de la cola
	public int getLongitud() {
		return this.longitud;
	}
	
	//Vacia
	public boolean Vacia() {
		return ((primero==null)?true:false);
	}
	
	
}
