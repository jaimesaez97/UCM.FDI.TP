package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.logic.multigames.Game;

public  class HelpCommand extends NoParamCommand{
	public HelpCommand (){
		super("help", "print this help message");
		
	}
	
	public boolean execute(Game game, Scanner in){
		
		System.out.println(CommandParser.commandHelp());
		return false;
	}

}
