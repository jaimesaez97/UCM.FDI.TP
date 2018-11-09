/*	DUDAS
 * 	EN la salida esperada se supone que el semáforo cambia de carretera
 *  AUNQUE NO SE HA CONSUMIDO EL TIEMPO DEL CRUCE, ¿no se supone que NO cambia?
 * */
package model.cruces;

import java.util.List;

import model.CarreteraEntranteConIntervalo;
import model.carreteras.Carretera;


public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo>{

	
	private boolean _first;

	public CruceCongestionado(String id) {
		super(id);
		this._first = true;
	}

	@Override
	protected void actualizaSemaforos() {
			// IF-ELSE sobra
		if(!this._first){
			if(this._indiceSemaforoVerde == -1) {	
		
				this._indiceSemaforoVerde = IDXgetColaMasLlena(this._carreterasEntrantes, -1);
				this._carreterasEntrantes.get(this._indiceSemaforoVerde).ponSemaforo(true);
			}
			else {
				
				if(this._carreterasEntrantes.get(this._indiceSemaforoVerde).tiempoConsumido()) {
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).ponSemaforo(false);
						// Se incializan los atributos de ri: -> ¿?¿?¿
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_intervalo(0);
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_unidadesTiempoUsadas(0);
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_usadaPorUnVehiculo(false);
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_usoCompleto(true);
					
					this._indiceSemaforoVerde = IDXgetColaMasLlena(this._carreterasEntrantes, this._indiceSemaforoVerde);
					//CarreteraEntranteConIntervalo rj = creaCarreteraEntrante(this._carreterasEntrantes.get(this._indiceSemaforoVerde)._road);
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).ponSemaforo(true);
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_intervalo(Math.max(this._carreterasEntrantes.get(this._indiceSemaforoVerde).numVehiculosEnCola()/2, 1));				
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_unidadesTiempoUsadas(0);
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_usadaPorUnVehiculo(false);
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_usoCompleto(true);
					
				}
			}
		}
		else{
			this._carreterasEntrantes.get(this._indiceSemaforoVerde).ponSemaforo(true);
			this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_intervalo(Math.max(this._carreterasEntrantes.get(this._indiceSemaforoVerde).numVehiculosEnCola()/2, 1));				
			this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_unidadesTiempoUsadas(0);
			this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_usadaPorUnVehiculo(false);
			this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_usoCompleto(true);
			this._first = false;
		}
		
	}

	public String generaInforme(int time){
		String report = "";
		report += "[junction_report]\n";
		report += "id = " + this._id + "\n";
		report += "time = " + time + "\n";
		report += "queues = ";
		for(int i = 0; i < this._carreterasEntrantes.size(); ++i) {
			CarreteraEntranteConIntervalo ce = this._carreterasEntrantes.get(i);
			report += "(";
			report += ce.getIdRoad() + ",";
			if(ce.getTrafficLight()){
				report += "green:";
				report += (ce.get_intervalo() - ce.get_unidadesTiempoUsadas());
			}				
			else
				report += "red";

			report += ",[";
			for(int x = 0; x < ce.getQueueSize(); ++x) {		
				report += ce.getIdRoadEntrante(x);
				if(x < ce.getQueueSize() - 1)
					report += ",";
			}
			
			
			report += "]";	
			report += ")";
			if(i < this._carreterasEntrantes.size() - 1) report += ",";
		}
		report += "\n";
		
		report += "type = mc";
		report += "\n";
		
		return report;
	}
	
	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera road) {
		CarreteraEntranteConIntervalo ri = new CarreteraEntranteConIntervalo(road);		
		//ri.setInterval(Integer.max(ri._queue.size()/2, 1));
		ri.setInterval(0);
		return ri;
	}
	
	private int IDXgetColaMasLlena(List<CarreteraEntranteConIntervalo> e, int descarte) {
		int max = 0;
		int x = 0;
		
		for(int i = 0; i <  this._carreterasEntrantes.size(); ++i) {
			if(this._carreterasEntrantes.get(i).getQueueSize() > max && i != descarte) {
				max = this._carreterasEntrantes.get(i).getQueueSize();
				x = i;
			}
		}
		if(x == descarte ) x = (x + 1) % this._carreterasEntrantes.size();
		if(this._carreterasEntrantes.size() == 1) x = 0;
		
		return x;
	}
}
