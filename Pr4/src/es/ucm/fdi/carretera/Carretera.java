package es.ucm.fdi.carretera;

import es.ucm.fdi.vehiculo.*;
import es.ucm.fdi.cruce.*;

public class Carretera {
	private int _id;
	private int _length;
	private int _maxSpeed;
	private Vehiculo[] _carList;
	private int _location;
	
	// ¿ORDEN?
	
	public Carretera (int id, int length, int maxSpeed) {
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._length = length;
		this._carList = new Vehiculo[this._length];
	}
	
	public void entraVehiculo (int id, int maxSpeed, Cruce[] way, 
							   int actualWay, int totalLength) {
		
		this._carList[this._location] = new Vehiculo(id, maxSpeed, 0, this, 
										this._length - this._location, way, actualWay + 1, 
										0, totalLength);
								/*
								 *  THIS.LOCATION¿?¿?¿?'¡
								 * */
	}
	
	public void saleVehiculo () {
		
	}
	
	//geters & setters
	public int getId () {
		return this._id;
	}
	public int getLong() {
		return this._length;
	}
}
