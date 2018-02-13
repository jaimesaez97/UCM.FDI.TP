package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.control.commands.NoParamCommand;
import tp.pr3.logic.multigames.Game;

public class UndoCommand extends NoParamCommand {

	// SOLO PUEDE EJECUTARSE SOBRE 
	// LOS �LTIMOS 20 MOVIMIENTOS
	
	public UndoCommand () {
		super("undo", "undo the last move");
	}
	
	public boolean execute(Game game, Scanner in){
		game.undo();
		return true;
	}
}
