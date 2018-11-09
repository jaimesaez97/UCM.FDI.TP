package control;

import es.ucm.fdi.ini.*;

public abstract class ConstructorEventos {
	protected String _etiqueta; // "new road",...
	protected String[] _keys;	// "time", ...
	
	protected ConstructorEventos() {	
			// Cada clase dará los valores correspondientes
		this._etiqueta = null;
		this._keys = null;
	}
	
	public abstract Evento parser(IniSection sec); // parser de cada HERENCIA
	
	protected static String identificadorValido(IniSection sec, String key){
		String s = sec.getValue(key);
		
		if(!esIdentificadorValido(s))
			throw new IllegalArgumentException("El valor " + s + " para " + key + " no es un ID valido");
		
		else return s;
	}
	
	private static boolean esIdentificadorValido(String id){
		return id != null && id.matches("[a-z0-9_]+");
	}
	
	protected static int parseaInt(IniSection sec, String key){
		String v = sec.getValue(key);
		if(v == null) throw new IllegalArgumentException("Valor inexistente para la clave " + key);
		else return Integer.parseInt(sec.getValue(key));
	}
	
	protected static int parseaInt(IniSection sec, String key, int _default){
		String v = sec.getValue(key);
		return (v != null) ? Integer.parseInt(sec.getValue(key)) : _default;
	}
	
	protected static int parseaIntNoNegativo (IniSection sec, String key, int _default){
		int i = ConstructorEventos.parseaInt(sec, key, _default);
		
		if (i < 0) throw new IllegalArgumentException("El valor " + i + " para " + key + " no es un ID valido");
		
		else return i;
	}

	public abstract String template();
}
