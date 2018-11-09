/*	DUDAS
		Cuando en 14 pasa de time = 3 a time = 4, la carretera r2 
		pasa su intervalo a 2 y en la salida se espera que cambie
		a (r4, green:2,[]) pero, ¿no era que salia por pantalla 
		maxValorIntervalo - unidadesTiempoUsadas? En ese caso 
		cada vez que se resetee el cruce empezaria en el mismo numero, ¿verdad?
*/
package model.cruces;

import model.CarreteraEntranteConIntervalo;
import model.carreteras.Carretera;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo>{

	protected int _minValor;
	protected int _maxValor;
	
	public CruceCircular(String id, int min, int max) {
		super(id);
		this._maxValor = max;
		this._minValor = min;
	}	

	@Override
	protected void actualizaSemaforos() {
		
		if(this._indiceSemaforoVerde == -1) {
			this._indiceSemaforoVerde++;
			this._carreterasEntrantes.get(this._indiceSemaforoVerde).ponSemaforo(true);
		}
		else{
			if(this._carreterasEntrantes.get(this._indiceSemaforoVerde).tiempoConsumido()){
				this._carreterasEntrantes.get(this._indiceSemaforoVerde).ponSemaforo(false);
				if(this._carreterasEntrantes.get(this._indiceSemaforoVerde).usoCompleto())
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).setInterval(Integer.min(this._carreterasEntrantes.get(this._indiceSemaforoVerde).get_intervalo() + 1, this._maxValor));
					//ri._intervalo = Integer.min(ri._intervalo + 1, this._maxValor);
				else if(!this._carreterasEntrantes.get(this._indiceSemaforoVerde).usada())
					this._carreterasEntrantes.get(this._indiceSemaforoVerde).setInterval(Integer.max(this._carreterasEntrantes.get(this._indiceSemaforoVerde).get_intervalo() - 1, this._minValor));
					//ri._intervalo = Integer.max(ri._intervalo - 1, this._minValor); // con ri._intervalo - 1 falla
				
				this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_usadaPorUnVehiculo(false);
				this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_usoCompleto(true);
				this._carreterasEntrantes.get(this._indiceSemaforoVerde).set_unidadesTiempoUsadas(0);
				
				// Cuando sale de aqui con ri._unidadesUsadas = 2 y va 
				// a avanza se ponen unidades usadas a 3 -> ARREGLAR
				this._indiceSemaforoVerde = (this._indiceSemaforoVerde + 1) % this._carreterasEntrantes.size();
				this._carreterasEntrantes.get(this._indiceSemaforoVerde).ponSemaforo(true);		
			}
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
				//Cuando en 14 pasa de time = 3 a time = 4, la carretera r2 
				// pasa su intervalo a 2 y en la salida se espera que cambie
				// a (r4, green:2,[]) pero, ¿no era que salia por pantalla 
				// maxValorIntervalo - unidadesTiempoUsadas? En ese caso 
				// cada vez que se resetee el cruce empezaria en el mismo numero, ¿verdad?
				report += (ce.get_intervalo() - ce.get_unidadesTiempoUsadas());	// �ES + 1?
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
		
		report += "type = rr";
		report += "\n";
		
		return report;	
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera road) {
		CarreteraEntranteConIntervalo ri = new CarreteraEntranteConIntervalo(road);
		ri.setInterval(this._maxValor);
		return ri;
	}

}
