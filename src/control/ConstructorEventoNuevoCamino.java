package control;


import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCamino extends ConstructorEventos {
	
	protected String[] _valoresPorDefecto;
	public ConstructorEventoNuevoCamino() {
		super();
		this._etiqueta = "new_road";
		this._keys = new String[] { "id", "time", "src", "dest", "max_speed", "length", "type"};
		this._valoresPorDefecto = new String[] {" ", " ", " ", " ", " ", " ", "dirt",};
	}
	@Override
	public Evento parser(IniSection sec) {
		if (!sec.getTag().equals(this._etiqueta) || !sec.getValue("type").equals("dirt")) return null;
		else
		return new EventoNuevoCamino(
										ConstructorEventos.parseaIntNoNegativo(sec, "time", 0),
										
										ConstructorEventos.identificadorValido(sec, "id"),
										
										ConstructorEventos.identificadorValido(sec, "src"),
										
										ConstructorEventos.identificadorValido(sec, "dest"),
										
										ConstructorEventos.parseaIntNoNegativo(sec, "max_speed", 0),
										
										ConstructorEventos.parseaIntNoNegativo(sec, "length", 0)

										);
	}
	public String toString () {
		return "Nuevo Camino";
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
		s += "type = dirt\n";
		
		return s;
	}
}
