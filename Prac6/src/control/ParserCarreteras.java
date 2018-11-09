package control;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.NotValidItinerary;
import model.cruces.CruceGenerico;

public class ParserCarreteras {

	public static List<CruceGenerico<?>> parseaListaCruces (String[] way, MapaCarreteras map) throws CantFindOnMap, NotValidItinerary {
		List<CruceGenerico<?>> _way = new ArrayList<CruceGenerico<?>>();
		if(way.length < 2) throw new NotValidItinerary();
		for(int i = 0; i < way.length; ++i){
			if(map.getCruce(way[i]) != null)
				_way.add(map.getCruce(way[i]));
			else
				throw new CantFindOnMap(way[i]);
		}
		return _way;
	}
}
