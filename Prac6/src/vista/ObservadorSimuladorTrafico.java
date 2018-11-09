package vista;

import es.ucm.fdi.exception.ErrorDeSimulacion;
import control.*;
import java.util.List;

public interface ObservadorSimuladorTrafico {
		// notifica errores
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e);
		// notifica el avance de los objetos de simulacion
	public void avanza(int time, MapaCarreteras map, List<Evento> events);
		// notifica que se ha generado un nuevo evento
	public void addEvento(int time, MapaCarreteras map, List<Evento> events);
		// notifica que se ha reiniciado la simulacion
	public void reinicia(int time, MapaCarreteras map, List<Evento> events);
	
	
	public String toString();

}
