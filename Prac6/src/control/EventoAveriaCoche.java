package control;



import es.ucm.fdi.exception.CantFindOnMap;

public class EventoAveriaCoche extends Evento{
	
	protected String[] _vehicles;
	protected int _duration;

	public EventoAveriaCoche(int tiempo, String vehicles, int duration) {
		super(tiempo);
		this._vehicles = vehicles.split(",");
		this._duration = duration;
	}

	@Override
	public void ejecuta(MapaCarreteras map) {
		try{
			for(int i = 0; i < this._vehicles.length; i++)
				map.getCar(this._vehicles[i]).setTiempoAveria(this._duration);
		}catch(CantFindOnMap e){
			System.out.println(e.getMessage());
			System.out.println("No se le puede a�adir una aver�a a un veh�culo que no existe");
		}
	}

	public String toString () {
		return "make_vehicle_faulty";
	}
}
