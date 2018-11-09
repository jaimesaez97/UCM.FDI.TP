package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCoche extends ConstructorEventos {

	protected String[] _valoresPorDefecto;
	
	public ConstructorEventoNuevoCoche() {
		this._etiqueta = "new_vehicle";
		this._keys = new String[] {"time", "id" , "itinerary", "max_speed", "resistance","fault_probability", "max_fault_duration",  "seed", "type"};
		this._valoresPorDefecto = new String[] {"", "", "car", "", "", "", "", "", "", };
	}

	@Override
	public Evento parser(IniSection sec) {
		if (!sec.getTag().equals(this._etiqueta) || !(sec.getValue("type").equals("car"))) return null;
		else
			return new EventoNuevoCoche(
					ConstructorEventos.identificadorValido(sec, "id"),
					ConstructorEventos.parseaIntNoNegativo(sec, "max_speed", 0),
					ConstructorEventos.parseaIntNoNegativo(sec, "time", 0),
					ConstructorEventos.parseaIntNoNegativo(sec, "km_ultima_averia", 0),
					ConstructorEventos.parseaIntNoNegativo(sec, "resistance", 0),
					ConstructorEventos.parseaIntNoNegativo(sec, "max_fault_duration", 0),
					Float.parseFloat(sec.getValue("fault_probability")),
					ConstructorEventos.parseaIntNoNegativo(sec, "seed", 0),		// LONG / INT
					sec.getValue("itinerary").split(",")
					);
	}
	
	public String toString () {
		return "Nuevo Coche";
	}

	@Override
	public String template() {
		String s = "";
		
		s += "[" + this._etiqueta + "]\n";
		s += "time = \n";
		s += "id = \n";
		s += "max_speed = \n";
		s += "itinerary = \n";
		s += "max_fault_duration = \n";
		s += "resistance = \n";
		s += "fault_probability = \n";
		s += "seed = \n";
		s += "type = car\n";
		
		return s;
	}
}
