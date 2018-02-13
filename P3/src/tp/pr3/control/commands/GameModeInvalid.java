package tp.pr3.control.commands;

import tp.pr3.logic.multigames.GameType;

@SuppressWarnings("serial")
public class GameModeInvalid extends Exception{
	
	private static String s = GameType.externaliseAll();

	
	public GameModeInvalid(){
	super("El modo de juego seleccionado no es valido o no existe(Solo: " + s + " .");

	}
}
