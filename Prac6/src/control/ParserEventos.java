package control;

import es.ucm.fdi.ini.IniSection;

public class ParserEventos {	
	private static ConstructorEventos[] eventos = {
		new ConstructorEventoNuevoCruce(),
		new ConstructorEventoNuevoCruceCongestionado(),
		new ConstructorEventoNuevoCruceCircular(),
		new ConstructorEventoNuevoVehiculo(),
		new ConstructorEventoNuevaCarretera(),
		new ConstructorEventoAveriaCoche(),
		new ConstructorEventoNuevoCoche(),
		new ConstructorEventoNuevaBicicleta(),
		new ConstructorEventoNuevoCamino(),
		new ConstructorEventoNuevaAutopista()
	} ;
	
	// bucle de prueba y error
	public static Evento parseaEvento (IniSection sec) {
		int i = 0;
		boolean seguir = true;
		Evento e = null;
		
		while(i < ParserEventos.eventos.length && seguir){
			// ConstructorEventos contiene este metodo
			e = ParserEventos.eventos[i].parser(sec);
			if (e != null) seguir = false;
			else ++i;	
		} 	
		
		return e;
	}

	public static ConstructorEventos[] getConstructoresEventos() {
		// TODO Auto-generated method stub
		return eventos;
	}
	
}
