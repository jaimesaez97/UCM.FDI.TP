package model;

import java.util.ArrayList;
import java.util.List;

import model.carreteras.Carretera;
import model.vehiculos.Vehiculo;
import es.ucm.fdi.exception.CantFindOnMap;

public class CarreteraEntrante {
	
	protected Carretera _road;
	protected List<Vehiculo> _queue;
	protected boolean _trafficLight;		// true -> green
	
	public CarreteraEntrante(Carretera road) {
		this._road = road;	
		this._queue = new ArrayList<Vehiculo>();
		this._trafficLight = false;
	}
	
	public void avanzaPrimerVehiculo() throws CantFindOnMap{
		if(!_queue.isEmpty())
			try{
			this._queue.remove(0).moverASiguienteCarretera();
			}catch(CantFindOnMap e){
				System.out.println(e.getMessage());
			}
	}
		
		// a�adida yo	
	public void entraVehiculoAlaCola(Vehiculo car){
		this._queue.add(car);
	}
	
	public String getIdRoadEntrante(int x){
		return this._queue.get(x).getId();
	}
	
	public boolean getTrafficLight(){
		return this._trafficLight;
	}
	
	// setters
	public void ponSemaforo(boolean color){
		this._trafficLight = color;
	}
	
	// getters
	boolean isEmpty(){
		return this._queue.size() == 0;
	}
	
	public int numVehiculosEnCola() {
		return this._queue.size();
	}
	
	public String getIdRoad() {
		return this._road.getId();
	}
	
	public int getQueueSize(){
		return this._queue.size();
	}
	
	public String toString() {
		String report = "";
		
		report += this._road.getId() + ",";
		if(this._trafficLight)
			report += "green,";
		else
			report += "red,";
		report += "[";
		for(int i = 0; i < this._queue.size(); ++i) {		
			report += this._queue.get(i).getId();
			if(i < this._queue.size() - 1)
				report += ",";
		}
		report += "]";
		
	
		return report;
	}

	public Carretera getCarretera() {
		return this._road;
	}

	public boolean tieneSemaforoVerde() {
		return this._trafficLight;
	}
}
