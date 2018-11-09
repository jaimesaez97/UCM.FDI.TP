package es.ucm.fdi.exception;

@SuppressWarnings("serial")
public class JunctionAlreadyAdded extends Exception{

	public JunctionAlreadyAdded(String _id) {
		super("Junction " + _id + " already added to the map");
	}
}
