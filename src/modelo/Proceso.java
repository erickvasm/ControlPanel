package modelo;

//Esta Clase contiene la descripciones del proceso
public class Proceso {
	
	//Atributos del proceso
	private int ID=0;//ID del proceso
		//>Datos extraidos de las cadenas de carecteres prosendentes del archivo de descripciones
	private int TiempoLlegada=0;//Tiempo de llegada (desde archivo)
	private int PrioridadInicial=0;//Prioridad inicial (desde archivo)
	private int TiempoRequerido=0;//Tiempo de Procesado (desde archivo)
	private int MemoriaRequerida=0;//Memoria requerida (desde archivo)
	private int ImpresorasSolicitas=0;//Impresoras solicitas (desde archivo)
	private int EscaneresSolicitados=0;//Escaneres solicitados(desde archivo)
	private int ModemSolicitado=0;//Modems solicitados (desde archivo)
	private int CDSolicitados=0;//CD solicitados (desde archivo)
	
	//>Datos de Operacion
	private int Estado=0;//Estado del proceso 1-corriendo,2-listo 3- bloqueado
	private int PrioridadActual=0;//Prioridad actual del proceso
	private int TiempoRestante=0;//Tiempo de procesado restante
	private String UbicacionMemoria="";//Ubicacion en memoria
	private int ModemAsignadas=0;//Modems asignadas
	private int ImpresorasAsignadas=0;//Impresoras asignadas
	private int EscaneresAsignados=0;//Escaneres asignados
	private int CDAsignados=0;//CD asignados
	private String colorMemo="";

	//Constructor Proceso
	public Proceso() {
		//VACIO
	}

	
									//METODOS SETTER & GETTER
	
	//Todos los siguientes metodos sirven para obtener y modificar los distintos atributos de esta clase					
	
	public int getTiempoLlegada() {
		return TiempoLlegada;
	}

	public void setTiempoLlegada(int tiempoLlegada) {
		TiempoLlegada = tiempoLlegada;
	}

	public int getPrioridadInicial() {
		return PrioridadInicial;
	}

	public void setPrioridadInicial(int prioridadInicial) {
		PrioridadInicial = prioridadInicial;
	}

	public int getTiempoRequerido() {
		return TiempoRequerido;
	}

	public void setTiempoRequerido(int tiempoRequerido) {
		TiempoRequerido = tiempoRequerido;
	}

	public int getMemoriaRequerida() {
		return MemoriaRequerida;
	}

	public void setMemoriaRequerida(int memoriaRequerida) {
		MemoriaRequerida = memoriaRequerida;
	}

	public int getImpresorasSolicitas() {
		return ImpresorasSolicitas;
	}

	public void setImpresorasSolicitas(int impresorasSolicitas) {
		ImpresorasSolicitas = impresorasSolicitas;
	}

	public int getEscaneresSolicitados() {
		return EscaneresSolicitados;
	}

	public void setEscaneresSolicitados(int escaneresSolicitados) {
		EscaneresSolicitados = escaneresSolicitados;
	}

	public int getModemSolicitado() {
		return ModemSolicitado;
	}

	public void setModemSolicitado(int modemSolicitado) {
		ModemSolicitado = modemSolicitado;
	}

	public int getCDSolicitados() {
		return CDSolicitados;
	}

	public void setCDSolicitados(int cDSolicitados) {
		CDSolicitados = cDSolicitados;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


	public int getEstado() {
		return Estado;
	}


	public void setEstado(int estado) {
		Estado = estado;
	}


	public int getPrioridadActual() {
		return PrioridadActual;
	}


	public void setPrioridadActual(int prioridadActual) {
		PrioridadActual = prioridadActual;
	}


	public int getTiempoRestante() {
		return TiempoRestante;
	}


	public void setTiempoRestante(int tiempoRestante) {
		TiempoRestante = tiempoRestante;
	}


	public String getUbicacionMemoria() {
		return UbicacionMemoria;
	}


	public void setUbicacionMemoria(String ubicacionMemoria) {
		UbicacionMemoria = ubicacionMemoria;
	}


	public int getImpresorasAsignadas() {
		return ImpresorasAsignadas;
	}


	public void setImpresorasAsignadas(int impresorasAsignadas) {
		ImpresorasAsignadas = impresorasAsignadas;
	}


	public int getEscaneresAsignados() {
		return EscaneresAsignados;
	}


	public void setEscaneresAsignados(int escaneresAsignados) {
		EscaneresAsignados = escaneresAsignados;
	}


	public int getCDAsignados() {
		return CDAsignados;
	}


	public void setCDAsignados(int cDAsignados) {
		CDAsignados = cDAsignados;
	}


	public int getModemAsignadas() {
		return ModemAsignadas;
	}


	public void setModemAsignadas(int modemAsignadas) {
		ModemAsignadas = modemAsignadas;
	}


	public String getColorMemo() {
		return colorMemo;
	}


	public void setColorMemo(String colorMemo) {
		this.colorMemo = colorMemo;
	}
	
	
	
	

}
