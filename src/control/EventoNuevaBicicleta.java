package control;

import java.util.List;

import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.CarAlreadyAdded;
import es.ucm.fdi.exception.NotValidItinerary;
import model.cruces.CruceGenerico;
import model.vehiculos.Bicicleta;

public class EventoNuevaBicicleta extends Evento{

	protected String _id;
	protected int _maxSpeed;
	protected String[] _way;
	
	public EventoNuevaBicicleta(int tiempo, String id, int maxSpeed, String[] iti) {
		super(tiempo);
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._way = iti;
	}

	@Override
	public void ejecuta(MapaCarreteras map){
			// Creamos la bici
		List<CruceGenerico<?>> iti;
		try {
			iti = ParserCarreteras.parseaListaCruces(this._way, map);
			try{
		Bicicleta bike = new Bicicleta(this._id, this._maxSpeed, iti);
			
			// La añadimos al mapa
		map.addVehiculo(this._id, bike);
			}catch(CarAlreadyAdded m){
				System.out.println(m.getMessage());
			}
		}catch (CantFindOnMap e){
			System.out.println(e.getMessage());
		} catch (NotValidItinerary e1) {
			System.out.println(e1.getMessage());

		}
	}
	
	public String toString() {
		return "new_bike";
	}

}
