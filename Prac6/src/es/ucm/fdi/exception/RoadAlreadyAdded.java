package es.ucm.fdi.exception;

@SuppressWarnings("serial")
public class RoadAlreadyAdded extends Exception{

	public RoadAlreadyAdded(String _id){
		super("Road " + _id + " already added to the map");
	}
}
