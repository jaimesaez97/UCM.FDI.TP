package tp.pr2.control.commands;

@SuppressWarnings("serial")
public class GameModeInvalid extends Exception{
	
	public GameModeInvalid(){
		
	super("El modo de juego seleccionado no es valido o no existe(Solo original, inverse o fib)");

	}
}
