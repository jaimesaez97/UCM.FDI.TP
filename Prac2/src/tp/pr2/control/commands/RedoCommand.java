package tp.pr2.control.commands;

import java.util.Scanner;

import tp.pr2.control.commands.NoParamCommand;
import tp.pr2.logic.multigames.Game;

public class RedoCommand extends NoParamCommand{
	
	// SOLO SE PUEDE HACER SOBRE
	// LOS UNDOS HECHOS
	
	public RedoCommand() {
		super("redo", "redo the last command");
	}

	public boolean execute(Game game, Scanner in) {
		game.redo();
		return true;
	}
}
