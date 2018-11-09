/*
 * 	PREGUNTAS:
 * 		1.- ¿Cómo se escribe el informe si OutputStream.write() escribe int?
 * 		2.- En MapaCarreteras/add_____() -> ¿Se busca el STRING como Key, el OBJECT como VALUE o las 2?
 * 		3.- Cruce/addCarreteraEntranteAlCruce() -> ¿No debería pasarse el cruce destino?
 * 		4.- Carretera/avanza() -> ¿Bien hecho hacia atrás? ¿O replantear?
 * 		5.- Hay que añadir metodo entraVehiculoAlaCola(car) en CarreteraEntrante, ¿verdad?
 * 		6.- EventoNuevoCoche/ejecuta()
 * 		
 * 	--++
 * HACER:
 * 		1.- Terminar SimuladorTrafico/ejecuta() -> Hacer el paso de redactar informe	
													
 * */
package model.vehiculos;

import java.util.List;

import model.carreteras.Carretera;
import model.cruces.CruceGenerico;
import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulador.ObjetoSimulacion;

public class Vehiculo extends ObjetoSimulacion {

	protected Carretera _curRoad;
	protected int _maxSpeed;
	protected int _curSpeed;
	protected int _kilometraje;
	protected int _curLocationOnRoad;
	protected int _faultTime;
	

	protected List<CruceGenerico<?>> _way;
	protected int _actualWay;
	protected boolean _enCruce;
	protected boolean _isArrived;
	/*
	 * If _tiempoAveria > 0 the car keeps stopped, 
	 * else increments his _curLocationOnRoad accord 
	 * his _curSpeed
	 * */
	
	public Vehiculo(String id, int maxSpeed, List<CruceGenerico<?>> iti) {
		super(id);
			this._curRoad = null;
			this._maxSpeed = maxSpeed;
			this._curSpeed = 0;
			this._kilometraje = 0;		 
			this._curLocationOnRoad = 0;
			this._faultTime = 0;
			this._way = iti;
			this._enCruce = false;
	}
	
	public void completaDetallesSeccion(IniSection is) {
		is.setValue("speed", this._curSpeed);
		is.setValue("kilometraje", this._kilometraje);
		is.setValue("faulty", this._faultTime);
		is.setValue("location", this._isArrived ? "arrived" : this._curRoad + ":" + this.getCurLocationOnRoad());
	}
	@Override
	public void avanza () {
		/*
		 	Si el vehículo no está averiado (tiempoAveria es 0), entonces avanza su localización
			de acuerdo con su velocidad actual. La nueva localización será igual a la localización
			anterior más la velocidad actual.
		 */
		if(this.getTiempoInfraccion() > 0) {
			this.setTiempoAveria(this._faultTime - 1);
		}
		else if(!this._enCruce) {
				// Si el coche no está en un cruce
			int newLocation = 0;
			newLocation = this._curLocationOnRoad + this._curSpeed;
			if(newLocation >= this._curRoad.getLength()){
				this._actualWay++;
				
				/*	Si la nueva localización es igual o mayor que la
					longitud de la carretera por la que viaja, entonces pondremos su localización igual a
					la longitud de la carretera, y el vehículo entrará a la cola del correspondiente cruce
					(todas las carreteras tienen un cruce inicial y final).
				 
				*/
				
					this._kilometraje += (this._curRoad.getLength() - this._curLocationOnRoad);
					this._curSpeed = 0;
					this._curLocationOnRoad = this._curRoad.getLength();
					this._curRoad.entraVehiculoAlCruce(this);
					this._enCruce = true;	// Los vehiculos que espearn en la cola de un cruce no pueden avanzar
					
			}
			else{
				this._curLocationOnRoad += this._curSpeed;
				this._kilometraje += this._curSpeed;
			}			
		}
	}
	
	public void moverASiguienteCarretera () throws CantFindOnMap {
		if(this._curRoad != null) {
			this._curRoad.saleVehiculo(this);
		}
		if(this._actualWay == this._way.size() - 1) {	// ¿o -1?
				// Si el coche no tiene más carreteras
			this._isArrived = true;
			this._curRoad = null;
			this._curSpeed = 0;
			this._curLocationOnRoad = 0;	
			this._enCruce = true;
		}
		else {
				// Se calcula la siguiente carretera (si no existe-> excepcion)
			CruceGenerico<?> src = this._way.get(this._actualWay);
			CruceGenerico<?> dst = this._way.get(this._actualWay + 1);
			
			Carretera road = src.carreteraHaciaCruce(dst);	// carretera desde SRC hasta DST
			if(road == null) {
				throw new CantFindOnMap("Road to " + dst + " from " + src);
			}
			// Se introduce vehiculo en la carretera
			this._curRoad = road;
			// Se inicia su localizacion
			this._curLocationOnRoad = 0;
			this._curSpeed = 0;
			this._enCruce = false;	// ¿verdad?
			 road.entraVehiculo(this);
			 
		}
	}

	/*************NO NECESARIO***************/	
	public String generaInforme (int time) {
		String report = "";
		
		report += "[vehicle_report]\n";		
		report += "id = " + this._id + "\n";		
		report += "time = " + time + "\n";			
		report += "speed = " + this._curSpeed + "\n";		
		report += "kilometrage = " + this._kilometraje + "\n";		
		report += "faulty = " + this._faultTime + "\n";
		
		report += "location = ";
		if(this._isArrived)
			report += "arrived\n";
		else
			report += "(" + this._curRoad.getId() + "," + this._curLocationOnRoad + ")\n";
		
		
		return report;
	}
	
	
	// getters	
	public int getTiempoInfraccion() {
		return this._faultTime;
	}
	
	public int getCurLocationOnRoad () {
		return this._curLocationOnRoad;
	}
	// setters
	public void setTiempoAveria (int steps) {
		if(this._curRoad != null) {
			this._faultTime = Integer.max(0, steps);
			if(this._faultTime > 0) this.setVelocidadActual(0);
		}
	}
	
	public void setVelocidadActual (int speed) {
		//		this._curSpeed = Integer.min(Integer.max(speed, 0), this._maxSpeed);
		if(!this._enCruce)
			if(speed < 0)
				this._curSpeed = 0;
			else if (speed > this._maxSpeed)
				this._curSpeed = this._maxSpeed;
			else
			this._curSpeed = speed;	
	}

	protected String getNombreSeccion() {
		return "vehicle_report";
	}

	public Carretera getRoad() {
		return this._curRoad;
	}
	
	public int getSpeed(){
		return this._curSpeed;
	}
	
	public int getKm(){
		return this._kilometraje;
	}
	
	public List<CruceGenerico<?>> getWay(){
		return this._way;
	}
	
	public String getWayString() {
		String s = "[";
		for(int i = 0; i < this._way.size(); ++i) {
			s += this._way.get(i).getId();
			if(i < this._way.size() - 1)
				s += ",";
		}
		s += "]";
		return s;
	}

	
	
}
