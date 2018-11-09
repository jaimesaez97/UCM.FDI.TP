	package model.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CarreteraEntrante;
import model.carreteras.*;
import model.vehiculos.Vehiculo;
import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.ini.IniSection;

public class Cruce extends CruceGenerico<CarreteraEntrante>{	// CRUCE ES UN ARRAY DE CARRETERAS¿?
	protected int _indiceSemaforoVerde;
	protected List<CarreteraEntrante> _carreterasEntrantes;
		// para optimizar búsquedas en carreteras entrantes
	protected Map<String, CarreteraEntrante> _mapaCarreterasEntrantes;
	protected Map<Cruce, Carretera> _mapaCarreterasSalientes;
	
	public Cruce(String id) {
		super(id);
		this._indiceSemaforoVerde = 0;
		this._carreterasEntrantes = new ArrayList<>();
		this._mapaCarreterasEntrantes = new HashMap<String, CarreteraEntrante>();
		this._mapaCarreterasSalientes = new HashMap<Cruce, Carretera>();
	}
	
	public Carretera carreteraHaciaCruce (Cruce cruce) {
		// devuelve la carretera que llega desde THIS hasta CRUCE <-- MIRAR SECCION COMPROBAR
		return this._mapaCarreterasSalientes.get(cruce);
	 // return cruce._mapaCarreterasEntrantes.get(this);
			// carretera saliente de CRUCE y llega a THIS
	}
	
	public void addCarreteraEntranteAlCruce (String idCarretera, Carretera carretera) {
		CarreteraEntrante road = new CarreteraEntrante(carretera);
		this._carreterasEntrantes.add(road);
		this._mapaCarreterasEntrantes.put(idCarretera, road);
	}
	
	public void addCarreteraSalienteAlCruce (Cruce destino, Carretera road) {
		this._mapaCarreterasSalientes.put(destino, road);
	}
	
	public void entraVehiculoAlCruce (String idCarretera, Vehiculo car) {
		CarreteraEntrante road = this._mapaCarreterasEntrantes.get(idCarretera);
		road.entraVehiculoAlaCola(car);
	}
	
	protected void actualizaSemaforos() {
		// pone el semaforo de la carretera actual a rojo
		this._indiceSemaforoVerde = (this._indiceSemaforoVerde + 1) % this._carreterasEntrantes.size();
	}
	
	public String generaInforme (int time) {
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
	/*		AVANZA() ESTABA ASI:
	  		for(int i = 0; i < this._carreterasEntrantes.size(); i++){
				//if(this._indiceSemaforoVerde == 1) 	// No es ni verde ni rojo, es el índice del semáforo de la carretera
														// que esta en verde (solo hay 1 verde) de las 4 que hay en el cruce
			
				if(this._indiceSemaforoVerde == i) {
					this._carreterasEntrantes.get(i).avanzaPrimerVehiculo();
					this._carreterasEntrantes.get(i).ponSemaforo(true);
				}
				else {
					this._carreterasEntrantes.get(i).ponSemaforo(false);	// Limpieza de semaforos para generatereport
				}
			}*/
	
	public void avanza() throws CantFindOnMap {
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
			actualizaSemaforos();
			this._carreterasEntrantes.get(this._indiceSemaforoVerde).avanzaPrimerVehiculo();
		}
	}
	
	protected String getNombreSeccion () {
		return "junction_report";
	}
	
	protected void completaDetallesSeccion (IniSection is) {
		
	}

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera road) {
		return new CarreteraEntrante(road);
	}

	

	
	
}
