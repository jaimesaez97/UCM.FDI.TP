package es.ucm.fdi.exception;

@SuppressWarnings("serial")
public class ErrorDeSimulacion extends Exception {

	public ErrorDeSimulacion(String id) {
		super("Simulation error: " + id);
	}
}
