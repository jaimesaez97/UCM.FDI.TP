package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.logic.multigames.Game;

public  class ExitCommand extends NoParamCommand{
	public ExitCommand (){
		super("exit", "terminate the program");
	}

	public boolean execute(Game game, Scanner in){
		
		game.setFinished();
		return false;
	}

}
