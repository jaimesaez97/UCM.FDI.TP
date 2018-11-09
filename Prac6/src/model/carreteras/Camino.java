package model.carreteras;

import model.cruces.CruceGenerico;

public class Camino extends Carretera{

	public Camino(String id, int length, int maxSpeed, CruceGenerico<?> origen, CruceGenerico<?>  destino) {
		super(id, length, maxSpeed, origen, destino);
	}
	@Override
	protected int calculaVelocidadBase() {
		return this._maxSpeed;
	}
	
	@Override
	protected int calculaFactorReduccion (int obstaculos) {
		return obstaculos + 1;
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
		
		report += "type = dirt\n";
		
		return report;
	}

}
