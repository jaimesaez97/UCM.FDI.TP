package control;

import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.RoadAlreadyAdded;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;

public class EventoNuevaCarretera extends Evento {
	
	protected String _id;
	protected Integer _maxSpeed;
	protected Integer _length;
	protected String _idSrcJunction;
	protected String _idDstJunction;

	public EventoNuevaCarretera(int tiempo, String id, String src, String dst, int maxSpeed, int length) {
		super(tiempo);
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._length = length;
		this._idSrcJunction = src;
		this._idDstJunction = dst;
	}

	@Override
	public void ejecuta(MapaCarreteras map) {
		// Obtiene origen y destino usando el mapa
		try{
		CruceGenerico<?> origen = map.getCruce(this._idSrcJunction);
		CruceGenerico<?> destino = map.getCruce(this._idDstJunction);

		// Añade al mapa la carretera
			try{
			map.addRoad(this._id, origen, creaCarretera(origen, destino), destino);
			}catch(RoadAlreadyAdded e){
				System.out.println(e.getMessage());
	
			}
		}catch(CantFindOnMap e){
			System.out.println(e.getMessage());
		}
	}
	
	protected Carretera creaCarretera (CruceGenerico<?> origen, CruceGenerico<?> destino) {
		return new Carretera(this._id, this._length, this._maxSpeed, origen, destino);
	}
	
	public String toString () {
		return "new_road";
	}

}
