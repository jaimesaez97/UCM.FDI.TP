package control;

import es.ucm.fdi.exception.JunctionAlreadyAdded;
import model.cruces.Cruce;
import model.cruces.CruceGenerico;

public class EventoNuevoCruce extends Evento{
	
	protected String _id;

	public EventoNuevoCruce(int tiempo, String id) {
		super(tiempo);
		this._id = id;
	}

	@Override
	public void ejecuta(MapaCarreteras map){
		try{
			CruceGenerico<?> junction = creaCruce();
			map.addJunction(this._id, junction);
		}catch(JunctionAlreadyAdded e){
			System.out.println(e.getMessage());
		}
	}

	protected CruceGenerico<?> creaCruce(){
		return new Cruce(this._id);
	}
	
	public String toString () {
		return "new_junction";
	}
}
