package tp.pr3.control.commands;



import java.util.Scanner;

import tp.pr3.logic.multigames.Game;

public abstract class Command {
	// abstracta: POR ENCIMA DE OTRAS CLASES
	private String helpText;
	private String commandText;
	/*
	     protected -> solo accesible desde subclases
	  	 final     -> las subclases no pueden cambiar variable (commandName)
	 */
	
	protected final String commandName;
	
	
	public Command(String commandInfo, String helpInfo) {
		commandText = commandInfo;
		helpText = helpInfo;
		String[] commandInfoWords = commandText.split("\\s+");
		commandName = commandInfoWords[0];
	}
	
	public abstract boolean execute(Game game, Scanner in);
		
	public abstract Command parse(String[] commandWords, Scanner in) throws DirNotValid, GameModeInvalid, NotValidName, FileNotExists;
	
	
	public String helpText() {
		return " " + commandText + ": " + helpText;
	}
	
} // Command