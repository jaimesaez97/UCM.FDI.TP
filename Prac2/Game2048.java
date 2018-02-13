package tp.pr1;

import java.util.Scanner;
import java.util.Random;

public class Game2048 {
	
	//FALTA:
	//	-�HAY QUE INICIALIZAR RANDOM?
	// 	-�EST�N BIEN ECHOS LOS NEW?
	//	-�C�MO PASO SCANNER?
	
	/*Game2048: Es la clase que contiene el m�todo
main de la aplicaci�n. En este caso el m�todo main
lee los valores de los par�metros de la aplicaci�n (2, quiz� 3), crea
una nueva partida (objeto de la clase Game ), crea un controlador (objeto de la clase
Controller) con dicha partida, e invoca al m�todo run del controlador.*/

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
				seed = new Random().nextInt(1000); // Si s�lo hay 2 argumentos		
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
