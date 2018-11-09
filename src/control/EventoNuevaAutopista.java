package control;

import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.RoadAlreadyAdded;
import model.carreteras.Autopista;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;

public class EventoNuevaAutopista extends Evento {
	
	protected String _id;
	protected int _maxSpeed;
	protected int _length;
	protected String _idSrcJunction;
	protected String _idDstJunction;
	protected int _numCarriles;

	public EventoNuevaAutopista(int tiempo, String id, String src, String dst, int maxSpeed, int length, int numCarriles) {
		super(tiempo);
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._length = length;
		this._idSrcJunction = src;
		this._idDstJunction = dst;
		this._numCarriles = numCarriles;
	}
	
	protected Carretera creaCarretera(CruceGenerico<?> origen, CruceGenerico<?> destino) {
		return new Autopista(this._id, this._length, this._maxSpeed, origen, destino, this._numCarriles);
	}
	
	public void ejecuta(MapaCarreteras map){
		try{
		// Obtiene origen y destino usando el mapa
		CruceGenerico<?> origen = map.getCruce(this._idSrcJunction);
		CruceGenerico<?> destino = map.getCruce(this._idDstJunction);
		

		try{
		// Añade al mapa la carretera
		map.addRoad(this._id, origen, creaCarretera(origen, destino), destino);
		}catch(RoadAlreadyAdded e){
			System.out.println(e.getMessage());
		}
		}catch(CantFindOnMap e){
			System.out.println(e.getMessage());
		}
	}
	
	public String toString () {
		return "new_lanes";
	}

}
