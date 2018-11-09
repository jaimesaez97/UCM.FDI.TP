package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruce extends ConstructorEventos {

	@SuppressWarnings("unused")
	private String[] valoresPorDefecto;
	
	
	public ConstructorEventoNuevoCruce() {
		this._etiqueta = "new_junction";
		this._keys = new String[] {"time", "id"};
		this.valoresPorDefecto = new String[] {"", "", };
	}
	
	
	@Override
	public Evento parser(IniSection sec) {
		if (!sec.getTag().equals(this._etiqueta) || sec.getValue("type") != null) return null;
		else
			return new EventoNuevoCruce(
											//extrae el valor del campo "time" en la seccion (0-defecto)
										ConstructorEventos.parseaIntNoNegativo(sec, "time", 0),
											//extrae el valor del campo "id" de la seccion
										ConstructorEventos.identificadorValido(sec, "id"));		
	}
	
	public String toString () {
		return "Nuevo Cruce";
	}
	
	@Override
	public String template() {
		String s = "";
		
		s += "[" + this._etiqueta + "]\n";
		s += "time = \n";
		s += "id = \n";
		
		return s;
	}
}

