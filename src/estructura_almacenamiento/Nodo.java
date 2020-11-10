package estructura_almacenamiento;

//Importes
import modelo.Proceso;

//Nodo en la Cola
public class Nodo {

	Nodo siguiente;//referencia al siguiente
	Proceso dato;//tipo de dato (Proceso)
	
	//Nodo Proceso
	public Nodo(Proceso dato) {
		siguiente=null;
		this.dato=dato;
	}
	
	
}
