package es.ucm.fdi.exception;

@SuppressWarnings("serial")
public class CantFindOnMap extends Exception{
	
	public CantFindOnMap(String id) {
		super("Cant find on map this object: " +  id);
	}
}
