/*
 * 	PREGUNTAS PARTE B
 * 
 * 			IMPORTANTE: HAY ALGUN SITIO DE LA PRACTICA DONDE HAYAQUE DEJAR CRUCE EN VEZ DE CRUCE GENERICO?
 * 		4.-  Â¿Hace falta que el constructor de EventoNuevoCamino tenga 
 * 			 parametros como src, dst, length... (En cruce no estan)
 * 		5.-  Coche/avanza() Â¿CÃ³mo ponemos que se ha recorrido resistenciakm kilometros?
 * 			 super.kilometraje += this._resistenciakm?
 * 		8.-  Â¿CÃ³mo se accede a cola de vehiculos desde CarreteraEntranteConIntervalo/avanzaPrimerVehiculo()?
 * 		9.-  El metodo avanzaPrimerVehiculo de CarreteraEntranteConIntervalo es protected pero el de CarreteraEntrante
 * 			 es public, Â¿cÃ³mo hacemos?
 * 		10.- CruceCongestionado/actualizaSemaforos() -> Â¿QuÃ© significa: "se inicializan los atributos de CarreteraEntranteConIntervalo"?
 * 		11.- Â¿Hay que hacer setters en CarreteraEntranteConIntervalo para los atributos que en CruceGenerico/actualizaSemaforos() se pide actualizar?
 * 		12.- Hay que hacer funcion entraVehiculo()  CarreteraEntrante, ¿verdad?
 * 
 * 
 * 	HACER PARTE B:
 * 		1.- Comprobar MapaCarreteras->Â¿QuÃ© hay que dejar como Cruce?Â¿Nada?
 * 		2.- Comprobar (REHACER) FactorReduccion es generico
 * 		3.- Hacer Cruce/creaCarreteraEntrante()
 * 		4.- Comprobar y terminar CruceCircular/actualizaSemaforos()
 **/

package model.vehiculos;

import java.util.List;

import model.cruces.CruceGenerico;

public class Bicicleta extends Vehiculo {

	public Bicicleta(String id, int maxSpeed, List<CruceGenerico<?>> iti)  {
		super(id, maxSpeed, iti);
	
	}
	
	public void setTiempoAveria(int steps) {
			// FALLA AQUI
		if(this._curRoad != null && (this._curSpeed >= this._maxSpeed/2)) {
			this._faultTime = Integer.max(0, steps);
			if(this._faultTime > 0) this.setVelocidadActual(0);	
		}
	
		else if(this._curSpeed == 0 && this._faultTime > 0)
			this._faultTime--;
	
	}
	
	@Override
	public void avanza () {
		if(this.getTiempoInfraccion() > 0) {
			this.setTiempoAveria(this._faultTime - 1);
		}
		else if(!this._enCruce) {
				// Si el coche no está en un cruce
			int newLocation = 0;
			newLocation = this._curLocationOnRoad + this._curSpeed;
			
			if(newLocation >= this._curRoad.getLength()){
				this._actualWay++;
				
				this._kilometraje += (this._curRoad.getLength() - this._curLocationOnRoad);
				this._curLocationOnRoad = this._curRoad.getLength();
				this._curRoad.entraVehiculoAlCruce(this);
				this._enCruce = true;
				this._curSpeed = 0;
					
				
				
					/******************************/
					/**¿¿Mover aqui de carretera?**/
					/******************************/
			}
			else{
				this._curLocationOnRoad += this._curSpeed;
				this._kilometraje += this._curSpeed;
			}	
		}
	}
	
	@Override
	public String generaInforme (int time) {
		String report = "";
		
		report += "[vehicle_report]\n";
		
		report += "id = " + this._id + "\n";
		
		report += "time = " + time + "\n";
				
		report += "speed = " + this._curSpeed + "\n";
		
		report += "kilometrage = " + this._kilometraje + "\n";
		
		report += "faulty = " + this._faultTime + "\n";
			// FALLO EN CURLOCATIONONROAD
		report += "location = ";
		if(this._isArrived)
			report += "arrived\n";
		else
			report += "(" + this._curRoad.getId() + "," + this._curLocationOnRoad + ")\n";
		
		
		report += "type = bike\n";
		
		return report;
	}
}

