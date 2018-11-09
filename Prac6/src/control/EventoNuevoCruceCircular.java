package control;

import es.ucm.fdi.exception.JunctionAlreadyAdded;
import model.cruces.CruceCircular;
import model.cruces.CruceGenerico;

public class EventoNuevoCruceCircular extends EventoNuevoCruce {

	protected int _maxValor;
	protected int _minValor;
	
	
	public EventoNuevoCruceCircular(int tiempo, String id, int max, int min) {
		super(tiempo, id);
		this._maxValor = max;
		this._minValor = min;
	}

	@Override
	public void ejecuta(MapaCarreteras map) {
		try{
			CruceCircular junction = new CruceCircular(this._id, this._minValor, this._maxValor);
			map.addJunction(this._id, junction);
		}catch(JunctionAlreadyAdded e){
			System.out.println(e.getMessage());
		}
		
	}
	@Override
	protected CruceGenerico<?> creaCruce(){
		return new CruceCircular(super._id, this._minValor, this._maxValor);
	}
}
