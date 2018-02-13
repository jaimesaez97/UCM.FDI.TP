package tp.pr2.control.commands;

import java.util.Scanner;

import tp.pr2.logic.multigames.Game;

public  class ResetCommand extends NoParamCommand{
	public ResetCommand (){
		super("reset", "start a new game");
		
	}

	public boolean execute(Game game, Scanner in){
		game.reset();
		return true;
	}



}