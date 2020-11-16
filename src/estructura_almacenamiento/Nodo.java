package estructura_almacenamiento;

//Importes
import modelo.Proceso;

//Nodo en la Cola
public class Nodo {

	Nodo siguiente;//referencia al siguiente
	Proceso dato;//tipo de dato (Proceso)
	
	//Constructor Nodo Proceso
	public Nodo(Proceso dato) {
		//El Nodo adopta el valor del parametro
		siguiente=null;
		this.dato=dato;
	}
	
	
}
