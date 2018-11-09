	package control;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventos{
	@SuppressWarnings("unused")

	private String[] valoresPorDefecto;
	
	public ConstructorEventoNuevoCruceCongestionado() {
		this._etiqueta = "new_junction";
		this._keys = new String[] {"time", "id", "type"};
		this.valoresPorDefecto = new String[] {"", "", "mc"};
	}
	@Override
	public Evento parser(IniSection sec) {
		if(!sec.getTag().equals(this._etiqueta) || !sec.getValue("type").equals("mc")) return null;
		else {
			return new EventoNuevoCruceCongestionado(	ConstructorEventos.parseaIntNoNegativo(sec, "time", 0), 
														ConstructorEventos.identificadorValido(sec, "id")					
													);
		}
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce Congestionado";
	}
	
	@Override
	public String template() {
		String s = "";
		
		s += "[" + this._etiqueta + "]\n";
		s += "time = \n";
		s += "id = \n";
		s += "type = mc\n";
		
		return s;
	}

}
