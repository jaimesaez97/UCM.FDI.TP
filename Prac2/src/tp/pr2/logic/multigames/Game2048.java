package tp.pr2.logic.multigames;

import java.util.Random;
import java.util.Scanner;

import tp.pr2.control.Controller;
import tp.pr2.util.Rules2048;

public class Game2048 {

	public static void main (String[] args){
		int boardSize, initCells;
		Random random;
		long seed;
		
		try{
		// creamos la SEMILLA:
			if(args.length == 3) {
				seed = Long.parseLong(args[2]);
			}
			
			else {
				seed = new Random().nextInt(100); // Si s�lo hay 2 argumentos		
			}
		
			random = new Random(seed);
			
		// creamos un GAME:
			Scanner in = new Scanner(System.in);
			
			// guardamos BoardSize
			boardSize = Integer.parseInt(args[0]);
			
			//guardamos initCells
			initCells = Integer.parseInt(args[1]);
			
			//� se le pasa por parametro?
			Game game = new Game (boardSize, initCells, random, new Rules2048(), GameType.ORIG);
		// creamos un CONTROLLER:
						
			Controller controller = new Controller(game, in);
			
		// invocamos a RUN():
			controller.run();
			in.close();

		}
		catch(NumberFormatException em){
			System.out.println("Formato de numero invalido, imposible ejecutar el programa");
		}
		
	
	}
	
	
	
}
