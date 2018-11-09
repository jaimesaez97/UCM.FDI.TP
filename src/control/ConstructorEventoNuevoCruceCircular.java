package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruceCircular extends ConstructorEventos{

	@SuppressWarnings("unused")
	private String[] valoresPorDefecto;
	
	public ConstructorEventoNuevoCruceCircular() {
		this._etiqueta = "new_junction";
		this._keys = new String[] {"time", "id", "max_time_slice", "min_time_slice", "type"};
		this.valoresPorDefecto = new String[] {"", "", "rr", "", ""};	
		
	}
	@Override
	public Evento parser(IniSection sec) {
		if(!sec.getTag().equals(this._etiqueta) || !sec.getValue("type").equals("rr")) return null;	
		else {
			return new EventoNuevoCruceCircular(	ConstructorEventos.parseaIntNoNegativo(sec, "time", 0), 
													ConstructorEventos.identificadorValido(sec, "id"), 
													ConstructorEventos.parseaIntNoNegativo(sec, "max_time_slice", 0), 
													ConstructorEventos.parseaIntNoNegativo(sec, "min_time_slice", 0)
												);
		}
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce Circular";
	}
	
	@Override
	public String template() {
		String s = "";
		
		s += "[" + this._etiqueta + "]\n";
		s += "time = \n";
		s += "id = \n";
		s += "max_time_slice = \n";
		s += "min_time_slice = \n";
		s += "type = rr\n";
		
		return s;
	}

}
