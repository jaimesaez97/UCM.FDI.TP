package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaAutopista extends ConstructorEventos {

	protected String[] _valoresPorDefecto;
	public ConstructorEventoNuevaAutopista() {
		super();
		this._etiqueta = "new_road";
		this._keys = new String[] { "id", "time", "src", "dest", "max_speed", "length", "type", "lanes"};
		this._valoresPorDefecto = new String[] {" ", " ", " ", " ", " ", "lanes", " ",};
	}
	@Override
	public Evento parser(IniSection sec) {
		if (!sec.getTag().equals(this._etiqueta) || !sec.getValue("type").equals("lanes")) return null;
		else {
			return new EventoNuevaAutopista(	ConstructorEventos.parseaIntNoNegativo(sec, "time", 0), 
												ConstructorEventos.identificadorValido(sec, "id"), 
												ConstructorEventos.identificadorValido(sec, "src"), 
												ConstructorEventos.identificadorValido(sec, "dest"), 
												ConstructorEventos.parseaIntNoNegativo(sec, "max_speed", 0),
												ConstructorEventos.parseaIntNoNegativo(sec, "length", 0), 
												ConstructorEventos.parseaIntNoNegativo(sec, "lanes", 0)
												
											);
		}
	}
	
	public String toString () {
		return "Nueva Autopista";
	}
	
	@Override
	public String template() {
		String s = "";
		
		s += "[" + this._etiqueta + "]\n";
		s += "time = \n";
		s += "id = \n";
		s += "src = \n";
		s += "dest = \n";
		s += "max_speed = \n";
		s += "length = \n";
		s += "lanes = \n";
		s += "type = lanes\n";
		
		return s;
	}
}
