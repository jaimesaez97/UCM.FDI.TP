package tp.pr3.control.commands;

@SuppressWarnings("serial")
public class NotValidName extends Exception {

	public NotValidName() {
		super("El nombre de fichero introducido no es válido...");
	}

}
