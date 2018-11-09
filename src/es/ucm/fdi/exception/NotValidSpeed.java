package es.ucm.fdi.exception;

@SuppressWarnings("serial")
public class NotValidSpeed extends Exception{
	
	public NotValidSpeed() {
		super("Not valid speed for the car: negative value");
	}
}
