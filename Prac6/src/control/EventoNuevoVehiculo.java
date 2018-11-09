package control;

import java.util.List;

import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.CarAlreadyAdded;
import es.ucm.fdi.exception.NotValidItinerary;

public class EventoNuevoVehiculo extends Evento{
	
	protected String _id;
	protected Integer _maxSpeed;
	protected String[] _way;

	public EventoNuevoVehiculo(int tiempo, String id, int maxSpeed, String[] way) {
		super(tiempo);
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._way = way;
	}
	@Override
	public void ejecuta(MapaCarreteras map){
		try{
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this._way, map);	
			try{
			Vehiculo car = new Vehiculo(this._id, this._maxSpeed, iti);	
			map.addVehiculo(this._id, car);
			}catch(CarAlreadyAdded e){
				System.out.println(e.getMessage());
			}
		}catch(NotValidItinerary e){
			System.out.println(e.getMessage());
		} catch (CantFindOnMap e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	public String toString() {
		return "new_vehicle";
	}

}
