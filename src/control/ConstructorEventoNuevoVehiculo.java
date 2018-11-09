package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoVehiculo extends ConstructorEventos{
	
	protected String[] valoresPorDefecto;

	public ConstructorEventoNuevoVehiculo() {
		this._etiqueta = "new_vehicle";
		this._keys = new String[] {"time", "id" , "type", "max_speed", "itinerary"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "", "", };
	}

	@Override
	public Evento parser(IniSection sec) {
		if (!sec.getTag().equals(this._etiqueta) || (sec.getValue("type") != null)) return null;
		else {
			String[] s = sec.getValue("itinerary").split(",");
			return new EventoNuevoVehiculo(
				ConstructorEventos.parseaIntNoNegativo(sec, "time", 0),
				ConstructorEventos.identificadorValido(sec, "id"),
				ConstructorEventos.parseaIntNoNegativo(sec, "max_speed", 0),
				s);	
		}
	}
	
	public String toString(){
		return "Nuevo Vehiculo";
	}
	
	@Override
	public String template() {
		String s = "";
		
		s += "[" + this._etiqueta + "]\n";
		s += "time = \n";
		s += "id = \n";
		s += "itinerary = \n";
		s += "max_speed = \n";
		
		return s;
	}
}
