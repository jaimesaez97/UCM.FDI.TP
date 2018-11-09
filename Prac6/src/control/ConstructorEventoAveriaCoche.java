package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoAveriaCoche extends ConstructorEventos {
	
	protected String[] _valoresPorDefecto;
	
	
	public ConstructorEventoAveriaCoche() {
		super();
		this._etiqueta = "make_vehicle_faulty";		 //¿?
		this._keys = new String[] {"time", "vehicles", "duration"};	 // ¿?
		this._valoresPorDefecto = new String[] {" ", " ", " " };
	}
	@Override
	public Evento parser(IniSection sec) {
		if (!sec.getTag().equals(this._etiqueta)) return null;
		else
			return new EventoAveriaCoche(
											//extrae el valor del campo "time" en la seccion (0-defecto)
										ConstructorEventos.parseaIntNoNegativo(sec, "time", 0),
											//extrae el valor del campo "id" de la seccion
										//ConstructorEventos.identificadorValido(sec, "vehicles"),
										sec.getValue("vehicles"),	// porque puede haber varios vehiculos e 
																	// IdentificadorValido lo tratal mal( lleva coma)
										ConstructorEventos.parseaInt(sec, "duration"));		
	}
	
	public String toString () {
		return "Nueva Averia Coche";
	}
	
	@Override
	public String template() {
		String s = "";
		
		s += "[" + this._etiqueta + "]\n";
		s += "time = \n";
		s += "vehicles = \n";
		s += "duration = \n";
		
		return s;
	}

}
