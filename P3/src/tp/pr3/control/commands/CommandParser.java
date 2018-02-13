package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.logic.multigames.Direction;
import tp.pr3.logic.multigames.GameType;

public abstract class CommandParser{
	
	
	/*En cada casilla hay un comando creado, haremos un for para ver cual es el que tenemos que ejecutar*/
	private final static Command[] availableCommands = {
		new HelpCommand(), 
		new	ResetCommand(),
		new ExitCommand(), 
		new MoveCommand(Direction.UP),
		new UndoCommand(),
		new RedoCommand(),
		new PlayCommand(GameType.ORIG),
		new SaveCommand("hola.txt"),
		new LoadCommand("hola.txt")
	};
	
	public static Command parseCommand(String[] commandWords, Scanner in) throws UnknownCommand, NotValidName, FileNotExists, DirNotValid, GameModeInvalid {

	Command cm = null;
		for(Command cm1:availableCommands){
	
			cm = cm1.parse(commandWords, in);
				if(cm!=null)
					return cm;
		
		
		}
		throw new UnknownCommand();
	}
	
	public static String commandHelp(){
		String ret = "The available commands are: \n";
		
		for (int i = 0; i < availableCommands.length;i++){
			ret += availableCommands[i].helpText() + "\n";
		}
		
		ret += "-------------------------------------\n";
		
		return ret;
	}

	
	
}
