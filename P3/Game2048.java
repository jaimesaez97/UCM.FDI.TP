package tp.pr1;

import java.util.Scanner;
import java.util.Random;

public class Game2048 {
	
	//FALTA:
	//	-¿HAY QUE INICIALIZAR RANDOM?
	// 	-¿ESTÁN BIEN ECHOS LOS NEW?
	//	-¿CÓMO PASO SCANNER?
	
	/*Game2048: Es la clase que contiene el método
main de la aplicación. En este caso el método main
lee los valores de los parámetros de la aplicación (2, quizá 3), crea
una nueva partida (objeto de la clase Game ), crea un controlador (objeto de la clase
Controller) con dicha partida, e invoca al método run del controlador.*/

	public Random (long seed) {
		
	}
	
	public static void main (String[] args) {
		int boardSize, initCells;
		String act;
		Random random;
		long seed;
		
		// creamos la SEMILLA:
		
			if(args.length == 3) {
				seed = Long.parseLong(args[2]);
			}
			
			else {
				seed = new Random().nextInt(1000); // Si sólo hay 2 argumentos		
			}
		
			random = new Random(seed);
			
		// creamos un GAME:
			Scanner in = new Scanner(System.in);
			
			// guardamos BoardSize
			boardSize = Integer.parseInt(args[0]);
			
			//guardamos initCells
			initCells = Integer.parseInt(args[1]);
			
			
			Game game = new Game (boardSize, initCells, random);
		// creamos un CONTROLLER:
			Controller controller = new Controller(game, in);
			
		// invocamos a RUN():
			controller.run();
		
		
		in.close();
	}
}
