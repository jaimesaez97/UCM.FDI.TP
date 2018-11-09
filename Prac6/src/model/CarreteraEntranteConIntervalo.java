package model;

import model.carreteras.Carretera;
import es.ucm.fdi.exception.CantFindOnMap;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante{
	
	private int _intervalo;
	private int _unidadesTiempoUsadas;
	private boolean _usoCompleto;
	private boolean _usadaPorUnVehiculo;
	

	public CarreteraEntranteConIntervalo(Carretera road) {
		super(road);
		this.set_intervalo(0);
		this.set_unidadesTiempoUsadas(0);
		this.set_usoCompleto(true);
		this.set_usadaPorUnVehiculo(false);
		
	}
	
	public void avanzaPrimerVehiculo() throws CantFindOnMap {
		
		this.set_unidadesTiempoUsadas(this.get_unidadesTiempoUsadas() + 1);
		
		
		// actualizar usoCompleto:
		if(this._queue.isEmpty())
			this.set_usoCompleto(false);
		else {
			try{
				this._queue.remove(0).moverASiguienteCarretera();
				this.set_usadaPorUnVehiculo(true);
			}catch(CantFindOnMap e){
				System.out.println(e.getMessage());
			}
			
		}
	}
	
	public boolean tiempoConsumido() {
		return this.get_unidadesTiempoUsadas() >= this.get_intervalo();
	}
	
	public boolean usoCompleto() {
		return this.is_usoCompleto();
	}
	
	public boolean usada() {
		return this.is_usadaPorUnVehiculo();
	}
	
	// seter intervalo
	public void setInterval(int interval){
		this.set_intervalo(interval);
	}

	public int get_intervalo() {
		return _intervalo;
	}

	public void set_intervalo(int _intervalo) {
		this._intervalo = _intervalo;
	}

	public boolean is_usadaPorUnVehiculo() {
		return _usadaPorUnVehiculo;
	}

	public void set_usadaPorUnVehiculo(boolean _usadaPorUnVehiculo) {
		this._usadaPorUnVehiculo = _usadaPorUnVehiculo;
	}

	public boolean is_usoCompleto() {
		return _usoCompleto;
	}

	public void set_usoCompleto(boolean _usoCompleto) {
		this._usoCompleto = _usoCompleto;
	}

	public int get_unidadesTiempoUsadas() {
		return _unidadesTiempoUsadas;
	}

	public void set_unidadesTiempoUsadas(int _unidadesTiempoUsadas) {
		this._unidadesTiempoUsadas = _unidadesTiempoUsadas;
	}
	
/*	public String toString() {
		String report = "";
		
		report += this._road.getId() + ",";
		if(this._trafficLight)
			report += "green:";
		else
			report += "red:";
		report += ( - this._unidadesTiempoUsadas) + ",";
		report += "[";
			for(int i = 0; i < this._queue.size(); ++i) {		
				report += this._queue.get(i).getId();
				if(i < this._queue.size() - 1)
					report += ",";
		}
		report += "]";	
	
		return report;
	}
	*/

}
