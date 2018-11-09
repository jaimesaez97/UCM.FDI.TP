//
package model.carreteras;


import java.util.Comparator;
import java.util.List;

import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import control.SortedArrayList;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulador.ObjetoSimulacion;

public class Carretera extends ObjetoSimulacion {
	
	protected int _length;
	protected int _maxSpeed;
	protected CruceGenerico<?> _origin;
	protected CruceGenerico<?> _dest;	
	protected List<Vehiculo> _carList;
	protected Comparator<Vehiculo> _cmp;
	
	
	public Carretera (String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dst) {
		super(id);
		this._length = length;
		this._maxSpeed = maxSpeed;
		this._origin = src;
		this._dest = dst;
		this._cmp = new Comparator<Vehiculo>() { // AL SORTED ARRAY LIST LE PASAMOS EL COMPARADOR HECHO
		    public int compare(Vehiculo e1, Vehiculo e2) {
			    //METODO A UTILIZAR CON COMPARATOR
			    	if (e1.getCurLocationOnRoad() == e2.getCurLocationOnRoad()) return 0;
			    	else if (e1.getCurLocationOnRoad() < e2.getCurLocationOnRoad()) return 1;
			    	else return -1;
	    	}
		    //ESTO ORDENA EN ORDEN ASCENDENTE 
	    };
	    this._carList = new SortedArrayList<Vehiculo>(this._cmp);
	}
	

	public void entraVehiculo (Vehiculo car) {
		// SI NO ESTÃ�, SE AÃ‘ADE Y ORDENA	
		if(!this._carList.contains(car)) this._carList.add(car);		
	}
	
	public void saleVehiculo (Vehiculo car) {
		this._carList.remove(car);
	}
	
	public void entraVehiculoAlCruce (Vehiculo car) {
			// Añade el vehiculo al cruce DESTINO 
		this._dest.entraVehiculoAlCruce(super._id, car);
	}
	
	protected int calculaVelocidadBase () {
		int baseSpeed = 0;		
		
		int max = Integer.max(this._carList.size(), 1);
		baseSpeed = Integer.min(this._maxSpeed, (this._maxSpeed/max) + 1);
		
		return baseSpeed;
	}
	
	protected int calculaFactorReduccion (int obstaculos) {
		return (obstaculos >= 1) ? 2 : 1;
	}
	
	public void avanza() {		
		int obstaculos = 0;
		int size = this._carList.size();
		for (int i = 0; i < size; ++i) {
			
			if(this._carList.get(i).getTiempoInfraccion() > 0) 
				obstaculos++;	
			else 
				this._carList.get(i).setVelocidadActual(calculaVelocidadBase()/calculaFactorReduccion(obstaculos));
			
			this._carList.get(i).avanza();
			if(size > this._carList.size()) {	// Significa que hemos redimensionado lista Y HAY UN INDICE MENOS
				i--;
				size--;
			}
		}
		this._carList.sort(_cmp);
	}
	
	protected String getNombreSeccion () {
		return "road_report";
	}
	
	protected void completaDetallesSeccion (IniSection is) {
		for(int i = 0; i < this._carList.size(); ++i) {
			is.setValue("vehicles", "(" +this._carList.get(i).getId() + "," + this._carList.get(i).getCurLocationOnRoad() + "), ");
		}
	}
	
	public void ordenaLista() {
		this._carList.sort(this._cmp);
	}

	public String generaInforme(int time) {
		String report = "";
		
		report += "[road_report]\n";
		
		report += "id = " + this._id + "\n";
		
		report += "time = " + time + "\n"; // TIEMPO
		
		report += "state = ";
		
		for(int i = 0; i < this._carList.size(); ++i) {
			report += "(" + this._carList.get(i).getId() + "," + this._carList.get(i).getCurLocationOnRoad() + ")";
			if(i < this._carList.size() - 1)
				report += ",";
		}
		
		report += "\n";		
		
		return report;
	}
	
	//geters & setters
	public int getLength() {
		return this._length;
	}


	public CruceGenerico<?> getCruceDestino() {
		return this._dest;
	}


	public List<Vehiculo> getCars() {
		return this._carList;
	}
	
	public String getStringCars() {
		String s = "[";
		for(int i = 0; i < this._carList.size(); ++i) {
			s += this._carList.get(i).getId();
			if(i < this._carList.size()-1)
				s += ",";
		}
		s += "]";
		return s;
	}


	public CruceGenerico<?> getCruceOrigen() {
		return this._origin;
	}
	
	public int maxSpeed() {
		return this._maxSpeed;
	}



}
