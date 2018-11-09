package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaBicicleta extends ConstructorEventos {

	protected String[] _valoresPorDefecto;
	
	public ConstructorEventoNuevaBicicleta() {
		super();
		this._etiqueta = "new_vehicle";
		this._keys = new String[] {"time", "id" , "itinerary", "max_speed", "type"};
		this._valoresPorDefecto = new String[] {"", "", "", "", "", "bike"};
	}


	@Override
	public Evento parser(IniSection sec) {
		if (!sec.getTag().equals(this._etiqueta) || !sec.getValue("type").equals("bike")) return null;
		else {
			return new EventoNuevaBicicleta(
							ConstructorEventos.parseaIntNoNegativo(sec, "time", 0),
							ConstructorEventos.identificadorValido(sec, "id"),
							ConstructorEventos.parseaIntNoNegativo(sec, "max_speed", 0),
							sec.getValue("itinerary").split(",")
					);
		}
	}
	
	public String toString () {
		return "Nueva Bicicleta";
	}

	@Override
	public String template() {
		String s = "";
		
		s += "[" + this._etiqueta + "]\n";
		s += "time = \n";
		s += "id = \n";
		s += "itinerary = \n";
		s += "max_speed = \n";
		s += "type = bike\n";
		
		return s;
	}
}
