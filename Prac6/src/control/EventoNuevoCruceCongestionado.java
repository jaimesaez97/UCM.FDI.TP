package control;

import es.ucm.fdi.exception.JunctionAlreadyAdded;
import model.cruces.CruceCongestionado;
import model.cruces.CruceGenerico;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce{

	public EventoNuevoCruceCongestionado(int tiempo, String id) {
		super(tiempo, id);
	}

	@Override
	public void ejecuta(MapaCarreteras map) {
		try{
			CruceGenerico<?> junction = creaCruce();
			map.addJunction(this._id, junction);
		}catch(JunctionAlreadyAdded e){
			System.out.println(e.getMessage());
		}		
	}
	
	@Override
	protected CruceGenerico<?> creaCruce(){
		return new CruceCongestionado(this._id);
	}

}
