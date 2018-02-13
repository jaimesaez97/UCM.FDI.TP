package tp.pr2.control.commands;

import java.util.Scanner;

import tp.pr2.logic.multigames.Direction;
import tp.pr2.logic.multigames.GameType;

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
	
	public static Command parseCommand(String[] commandWords, Scanner in) throws UnknownCommand, NotValidName, FileNotExists {

	Command cm = null;
		for(Command cm1:availableCommands){
		try{
			cm = cm1.parse(commandWords, in);
				if(cm!=null)
					return cm;
		}
		catch(DirNotValid e){
			System.out.println(e.getMessage());
		}
		catch (GameModeInvalid ex){
			System.out.println(ex.getMessage());

		}
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
