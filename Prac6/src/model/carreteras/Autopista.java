package model.carreteras;

import model.cruces.CruceGenerico;
import es.ucm.fdi.ini.IniSection;

public class Autopista extends Carretera{

	private int _numCarriles;
	
	public Autopista(String id, int length, int maxSpeed, CruceGenerico<?> origen, CruceGenerico<?> destino, int numCarriles) {
		super(id, length, maxSpeed, origen, destino);
		this._numCarriles = numCarriles;
	}
	
	@Override
	protected int calculaVelocidadBase() {
		int max = Integer.max(this._carList.size(), 1);
		int p2 = ((super._maxSpeed * this._numCarriles) / max) + 1;
		return Integer.min(super._maxSpeed, p2);
	}
	
	@Override
	protected int calculaFactorReduccion(int obstacles) {
		return obstacles < this._numCarriles ? 1 : 2;
	}
	
	protected void completaDetallesSeccion(IniSection is) {
		
	}
	
	@Override
	public void avanza() {		
		int obstaculos = 0;
		int size = this._carList.size();
		for (int i = 0; i < size; ++i) {
			
			if(this._carList.get(i).getTiempoInfraccion() > 0) 
				obstaculos++;	
			/*else if(this._numCarriles <= obstaculos)
				this._carList.get(i).setVelocidadActual(calculaVelocidadBase()/calculaFactorReduccion(2));*/
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
		
		report += "type = lanes\n";
		
		
		return report;
	}
}
