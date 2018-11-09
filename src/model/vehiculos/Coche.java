package model.vehiculos;

import java.util.List;
import java.util.Random;

import model.cruces.CruceGenerico;


public class Coche extends Vehiculo{
	
	protected int _kmUltimaAveria;
	protected int _resistenciaKm;
	protected int _duracionMaximaAveria;
	protected double _probDeAveria;
	protected Random _numAleatorio;

	public Coche(String id, int maxSpeed, int resistencia, double prob, long semilla, int maxDur, List<CruceGenerico<?>> iti){
		super(id, maxSpeed, iti);
		this._resistenciaKm = resistencia;
		this._duracionMaximaAveria = maxDur;
		this._probDeAveria = prob;
		this._numAleatorio = new Random(semilla);
		this._kmUltimaAveria = 0;
	}
	
	public void avanza() {
		if(super.getTiempoInfraccion() > 0) {
			this._kmUltimaAveria = this._kilometraje;
		}
		else {
		
			if(  (this._resistenciaKm < (this._kilometraje - this._kmUltimaAveria))
				&& (_numAleatorio.nextDouble() < this._probDeAveria) ) {
					setTiempoAveria(this._numAleatorio.nextInt(this._duracionMaximaAveria )+ 1 );
					
				}
		}
			
		
		super.avanza();
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
		
		report += "location = ";
		if(this._isArrived)
			report += "arrived\n";
		else
			report += "(" + this._curRoad.getId() + "," + this._curLocationOnRoad + ")\n";
		

		report += "type = car\n";
		
		return report;
	}
	

}
