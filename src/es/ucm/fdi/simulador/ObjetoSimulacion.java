package es.ucm.fdi.simulador;

import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.ini.IniSection;

public abstract class ObjetoSimulacion {

	protected String _id;
	
	public ObjetoSimulacion (String id) {
		this._id = id;
	}
	
	public String toString() {
		String ret = "";
		
		return ret;
	}
	
	public String generaInforme (int time) {
		IniSection is = new IniSection(this.getNombreSeccion());
										// los métodos getNombreSeccion y completaDetallesSeccion
										// tendrán que implementarlos cada subclase de ObjetoSimulacion
		is.setValue("id", this._id);
		is.setValue("time", time);
		this.completaDetallesSeccion(is);
		return is.toString();
	}
	
	public abstract void avanza() throws CantFindOnMap;
	
	protected abstract String getNombreSeccion();
	
	protected abstract void completaDetallesSeccion(IniSection is);
	
	
	// getters
	public String getId() {
		return this._id;
	}
}
