package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaCarretera extends ConstructorEventos {

	protected String[] _valoresPorDefecto;
	public ConstructorEventoNuevaCarretera() {
		super();
		this._etiqueta = "new_road";
		this._keys = new String[] { "id", "time", "src", "dest", "max_speed", "length"};
		this._valoresPorDefecto = new String[] {" ", " ", " ", " ", " ", " ", };
	}

	@Override
	public Evento parser(IniSection sec) {
		if (!sec.getTag().equals(this._etiqueta) || sec.getValue("type") != null) return null;
		else
			return new EventoNuevaCarretera(
											//extrae el valor del campo "time" en la seccion (0-defecto)
										ConstructorEventos.parseaIntNoNegativo(sec, "time", 0),
											//extrae el valor del campo "id" de la seccion
										ConstructorEventos.identificadorValido(sec, "id"),
											// source
										ConstructorEventos.identificadorValido(sec, "src"),
											// destino
										ConstructorEventos.identificadorValido(sec, "dest"),
											// maxSpeed
										ConstructorEventos.parseaIntNoNegativo(sec, "max_speed", 0),
											// length
										ConstructorEventos.parseaIntNoNegativo(sec, "length", 0)		);		
	}
	public String toString () {
		return "Nueva Carretera";
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
		
		return s;
	}

}
