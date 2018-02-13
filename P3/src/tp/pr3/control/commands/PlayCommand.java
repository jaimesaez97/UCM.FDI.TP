package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.logic.multigames.Game;
import tp.pr3.logic.multigames.GameType;

import java.util.Random;

public class PlayCommand extends Command {
	
	private GameType _type;
	private static String s = GameType.externaliseAll();
	
	public PlayCommand (GameType type) {
		super("play <game>", "start a new game of one of the game types: " + s);
		this._type = type;
	}

	public boolean execute(Game game, Scanner in) {
		int size, initCells;
		long seed;
		Random rand;
		/*PORQUE TENGO QUE PONER ESTO ??*/
		
		
		try{
		game.reset();
		
		
		System.out.println("Please enter the size of the board: ");
		size = Integer.parseInt(in.nextLine());
		if(size <= 0){
			System.out.println("Using the default size of the board: 4");
			size = 4;
		}
		System.out.println("Please enter the number of initial cells: ");
		initCells = Integer.parseInt(in.nextLine());
		if(initCells <= 0){
			System.out.println("Using the default number of initial cells: 2");
			initCells = 2;
		}
		System.out.println("Please enter the seed for the pseudo-random number generator: ");
		seed = Long.valueOf(in.nextLine());
		if(seed <= 0){
			System.out.println("Using the default seed for the pseudo-random number generator: 924");
			System.out.println("");
			seed = 924;
		}
		rand = new Random(seed);
		
		game.playCommand(game, size, initCells, rand, this._type);
		}
		catch(NumberFormatException e){
			System.out.println("Formato de entrada invalido, por favor, ingrese datos validos...");

			execute(game, in);
		}
		
		return true;
	}

	//REFACTORIZADO : AHORA FUNCIONA
	public Command parse(String[] commandWords, Scanner in) throws GameModeInvalid {
	
		if(commandWords[0].equalsIgnoreCase(this.commandName)){
			
			// tiene que llamar a _type.parse(param.toLowerCase());
			
			if(commandWords[1].equalsIgnoreCase("original")){
				this._type = GameType.ORIG;
				return this;
			}
				else if(commandWords[1].equalsIgnoreCase("inverse")){
				this._type = GameType.INV;
				return this;
				}
				else if(commandWords[1].equalsIgnoreCase("fib")){
				this._type = GameType.FIB;
				return this;
				}	
				
			throw new GameModeInvalid();
			
			
		}
		else
		return null;
	}
}
