package es.ucm.fdi.exception;

@SuppressWarnings("serial")
public class CarAlreadyAdded extends Exception{

	public CarAlreadyAdded(String _id) {
		super("Car " + _id + " already added to the map");
	}
}
