package control;

import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.RoadAlreadyAdded;
import model.carreteras.Camino;
import model.cruces.CruceGenerico;

public class EventoNuevoCamino extends Evento {
	
	protected String _id;
	protected int _maxSpeed;
	protected int _length;
	protected String _idSrcJunction;
	protected String _idDstJunction;

	public EventoNuevoCamino(int tiempo, String id, String src, String dst, int maxSpeed, int length) {
		super(tiempo);
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._length = length;
		this._idSrcJunction = src;
		this._idDstJunction = dst;
	}
	
	
	@Override
	public void ejecuta(MapaCarreteras map) {
			// Creamos el camino
		try{
		CruceGenerico<?>  origen = map.getCruce(this._idSrcJunction);
		CruceGenerico<?>  destino = map.getCruce(this._idDstJunction);
		
		
		Camino cam = new Camino(this._id, this._length, this._maxSpeed, origen, destino);
			// Añadimos al mapa
			try{
			map.addRoad(this._id, origen, cam, destino);
			}catch(RoadAlreadyAdded e){
				System.out.println(e.getMessage());
	
			}
		}catch(CantFindOnMap e){
			System.out.println(e.getMessage());

		}
	}

	public String toString () {
		return "new_path";
	}

}
