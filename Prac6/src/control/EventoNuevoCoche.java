package control;

import java.util.List;

import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.CarAlreadyAdded;
import es.ucm.fdi.exception.NotValidItinerary;
import model.cruces.CruceGenerico;
import model.vehiculos.Coche;

public class EventoNuevoCoche extends Evento{

	protected String _id;
	protected int _maxSpeed;
	protected String[] _way;
	protected int _kmUltimaAveria;
	protected int _resistenciaKM;
	protected int _duracionMaximaAveria;
	protected double _probDeAveria;
	protected long _semilla;
	
	public EventoNuevoCoche(String id, int maxSpeed, int tiempo, int ultima, int resistencia, int maxDuration, double prob, long semilla, String[] iti) {
		super(tiempo);
		this._id = id;
		this._maxSpeed = maxSpeed;
		this._way = iti;
		this._kmUltimaAveria = ultima;
		this._resistenciaKM = resistencia;
		this._duracionMaximaAveria = maxDuration;
		this._probDeAveria = prob;
		this._semilla = semilla;
	}

	@Override
	public void ejecuta(MapaCarreteras map)  {
		// Crea el coche
		try{
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this._way, map);	
		Coche car = new Coche(this._id, this._maxSpeed, this._resistenciaKM, this._probDeAveria, this._semilla, this._duracionMaximaAveria, iti);
			try{
			// Lo a�ade al mapa de carreteras
			map.addVehiculo(this._id, car);
			}catch(CarAlreadyAdded e){
				System.out.println(e.getMessage());
			}
		}catch(NotValidItinerary e){
			System.out.println(e.getMessage());

		}catch(CantFindOnMap e){
			System.out.println(e.getMessage());

		}
	}
	
	public String toString () {
		return "new_car";
	}

}
