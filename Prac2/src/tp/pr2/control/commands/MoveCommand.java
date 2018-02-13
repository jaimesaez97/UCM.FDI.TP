package tp.pr2.control.commands;


import java.util.Scanner;

import tp.pr2.logic.multigames.Direction;
import tp.pr2.logic.multigames.Game;

public class MoveCommand extends Command{

	
	private Direction _dir;
	
	public MoveCommand(Direction dir) {
		super("move", "execute a move in one of the four directions, up, down, left, right");
		_dir = dir; 
	}


	public boolean execute(Game game, Scanner in) {
		game.move(_dir);
		//controller.setNoPrintGameState();
		
		// �se saca aqu�?
		return true;
	}


	public Command parse(String[] commandWords, Scanner in) throws DirNotValid {
		if(commandWords[0].equalsIgnoreCase(this.commandName)){
			_dir = StringToDir(commandWords[1]);
			if(_dir != null){
			return this;
			}
			else
				throw new DirNotValid();
		}
		else
		return null;
	}

	public Direction StringToDir (String s){
		switch(s.toUpperCase()){
		case "UP":
			return Direction.UP;
		case "DOWN":
			return Direction.DOWN;
		case "LEFT":
			return Direction.LEFT;
		
		case "RIGHT":
			return Direction.RIGHT;
		default:
			return null;
		}
	}
	
	
	
}

