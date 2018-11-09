/*	DUDAS:
 * 		1.- ¿Que intervalo pongo de inicializacion en CruceGenerico?
 * 
 * */

package model.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CarreteraEntrante;
import model.carreteras.Carretera;
import model.vehiculos.Vehiculo;
import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulador.ObjetoSimulacion;

abstract public class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion{

	protected int _indiceSemaforoVerde;
	protected List<T> _carreterasEntrantes;
		// para optimizar búsquedas en carreteras entrantes
	protected Map<String, T> _mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> _mapaCarreterasSalientes;
	protected boolean _firstTime;
	
	public CruceGenerico(String id) {
		super(id);
		this._indiceSemaforoVerde = -1;
		this._carreterasEntrantes = new ArrayList<T>();
		this._mapaCarreterasEntrantes = new HashMap<String, T>();
		this._mapaCarreterasSalientes = new HashMap<CruceGenerico<?>, Carretera>();
		this._firstTime = true;
	}

	@Override
	public void avanza() throws CantFindOnMap {
		if(this._indiceSemaforoVerde == -1) {
			this._indiceSemaforoVerde++;
		}
		if(!this._carreterasEntrantes.isEmpty()){
			for(int i = 0; i < this._carreterasEntrantes.size(); i++){
			//ASI FUNCIONA EL 09
				if(this._indiceSemaforoVerde == i) {
					this._carreterasEntrantes.get(i).ponSemaforo(true);
				}
				else {
					this._carreterasEntrantes.get(i).ponSemaforo(false);	// Limpieza de semaforos para generatereport
				}
			}
			//this.actualizaSemaforos();
			if(!this._firstTime)
				this._carreterasEntrantes.get(this._indiceSemaforoVerde).avanzaPrimerVehiculo();	
			this._firstTime = false;
			this.actualizaSemaforos();
		}
	}

	public Carretera carreteraHaciaCruce(CruceGenerico<?> dst) {
		return this._mapaCarreterasSalientes.get(dst);
	}
	

	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera road) {
		this._mapaCarreterasSalientes.put(destino, road);
	}

	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera road) {
		T ri = creaCarreteraEntrante(road);
		this._carreterasEntrantes.add(ri);
		this._mapaCarreterasEntrantes.put(idCarretera, ri);
		
	}

	public void entraVehiculoAlCruce(String idCarretera, Vehiculo car) {
		T ri = this._mapaCarreterasEntrantes.get(idCarretera);
		ri.entraVehiculoAlaCola(car);
	}
	
	abstract protected void actualizaSemaforos();
	abstract protected T creaCarreteraEntrante(Carretera road);
	
	@Override
	protected String getNombreSeccion() {
		return "junction_report";
	}
	
	public String generaInforme(int time) {
		String report = "";
		report += "[junction_report]\n";
		report += "id = " + this._id + "\n";
		report += "time = " + time + "\n";
		report += "queues = ";
		for(int i = 0; i < this._carreterasEntrantes.size(); ++i) {
			CarreteraEntrante ce = this._carreterasEntrantes.get(i);
			report += "(";
			report += ce.toString();
			report += ")";
			if(i < this._carreterasEntrantes.size() - 1) report += ",";
		}
		report += "\n";
		
		return report;
	}

	protected void completaDetallesSeccion(IniSection is) {

		
	}

	public List<T> getCarreteras() {
		return this._carreterasEntrantes;
	}
	
	public List<T> getCarreterasRojo(){
		List<T> carreteras = new ArrayList<T>();
		for(int i = 0; i < this._carreterasEntrantes.size(); ++i) {
			T ce = this._carreterasEntrantes.get(i);
			if(!ce.getTrafficLight())
				carreteras.add(ce);
				
		}
		return carreteras;
	}
	
	public List<T> getCarreterasVerde(){
		List<T> carreteras = new ArrayList<T>();
		for(int i = 0; i < this._carreterasEntrantes.size(); ++i) {
			T ce = this._carreterasEntrantes.get(i);
			if(ce.getTrafficLight())
				carreteras.add(ce);
				
		}
		return carreteras;
	}
	

}
