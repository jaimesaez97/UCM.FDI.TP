package es.ucm.fdi.vehiculo;

import es.ucm.fdi.carretera.*;
import es.ucm.fdi.cruce.*;

public class Vehiculo {
	/*
	 * For the moment it only gas till max speed
	 * */
	
	private int _id;
	
	private int _maxSpeed;
	private int _curSpeed;
	
	private Carretera _curRoad;
	private int _curLocationOnRoad;
	
	private Cruce[] _way;
	private int _actualWay; //¿Se lleva desde aqui?
	
	private int _faultTime;
	
	private int _totalLength;
	/*
	 * If _tiempoAveria > 0 the car keeps stopped, 
	 * else increments his _curLocationOnRoad accord 
	 * his _curSpeed
	 * */
	
	public Vehiculo (int id, int maxSpeed, int speed, Carretera road, 
				     int RoadLocation, Cruce[] way, int actualWay,
				     int faultTime, int totalLength){
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._curSpeed = speed;
		this._curRoad = road;
		this._curLocationOnRoad = RoadLocation;
		this._way = way;
		this._actualWay = actualWay;
		this._faultTime = faultTime;
		this._totalLength = totalLength;
	}
	
	public void avanzar () {
		if(this._faultTime > 0) {
			this.setTiempoAveria(this._faultTime - 1);
			//¿this.setVelocidadActual(0);?
		}
		else{
			int newLocation = 0;
			newLocation = this._curLocationOnRoad + this._curSpeed;
			
			if(newLocation >= this._curRoad.getLong()){
				this._totalLength += (this._curRoad.getLong() - this._curLocationOnRoad);
				// total = carretera - dónde estaba
				this._curLocationOnRoad = this._curRoad.getLong();
				
				// set the car on the corresponding cross queue
				// 	-> the cars that wait in a cross queue 
				//	   cant move along, so they keep there till 
				//	   the cross says that he should move on,
				//	   calling moverASiguienteCarretera
			}
			else{
				this._curLocationOnRoad = newLocation;
				this._totalLength += newLocation;
			}
			
		}
	}
	
	public void moverASiguienteCarretera () {
		
	}
	
	
	// setters
	public void setTiempoAveria (int steps) {
		this._faultTime = steps;
		this.setVelocidadActual(0);
	}
	public void setVelocidadActual (int speed) {
		if(speed > this._maxSpeed)
			this._curSpeed = this._maxSpeed;
		else
			this._curSpeed = speed;
	}
	public String generaInforme () {
		String ret = "\n";
		
		ret += "[vehicle_report]\n";
		
		ret += "id = " + this._id + "\n";
		
		ret += "time = " + "\n";
		
		ret += "speed = " + this._curSpeed + "\n";
		
		ret += "kilometrage = " + this._totalLength + "\n";
		
		ret += "faulty = " + this._faultTime + "\n";
		
		ret += "location = {" + this._curRoad.getId() + "," + this._curLocationOnRoad + "}\n";
		
		ret += "\n";
		
		return ret;
	}
	
	// añadidos por mi
	
}
