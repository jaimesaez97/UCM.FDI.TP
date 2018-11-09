package es.ucm.fdi.exception;

@SuppressWarnings("serial")
public class NotValidItinerary extends Exception{
	
	public NotValidItinerary() {
		super("Not valid itinerary: less than 2 junction");
	}
}
