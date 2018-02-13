package tp.pr1;
import java.util.Scanner;

public class Controller {
	// FALTA:
	//		-¿REST SOLO ESO?
	
	
	// atributos privados:
	private Game game;
	private Scanner in;

	public Controller (Game game, Scanner in) {
		this.game = game;
		this.in = in;
	}
	
	
	public void run () {
		in = new Scanner(System.in);
		
		
		do{
		
			// pedimos opción
			System.out.println("Introduzca una opción: move, reset o exit ¿help?");
			
			// pillamos NEXT en vez de NEXTLINE para poder
			// hacer Direccion <direction>
			String accion;
			accion = in.next();
			
			// ejecutamos segun accion
			switch(accion.toUpperCase()) {
			case "MOVE":
				System.out.println("MOVE...");
				System.out.println(" ");
				System.out.println(" ");
				exMove();
				
			case "RESET":
				System.out.println("RESET...");
				System.out.println(" ");
				System.out.println(" ");
				// ¿?
				
			case "EXIT":
				System.out.println("GAME OVER...");
				System.out.println(" ");
				System.out.println(" ");
	
				break;
				
			case "HELP":
				System.out.println("-----------------------------------------------------------------------------------------------");
				
				System.out.println("  							MANUAL DE AYUDA");
				
				System.out.println(" ");
				
				System.out.println(" -  Move <direction>: execute a move in one of the four directions, up, down, left, right");
				
				System.out.println(" -  Reset: start a new game");
				
				System.out.println(" -  Help: print this help message");
	
				System.out.println(" -  Exit: terminate the program");
				
				System.out.println(" ");
				
				System.out.println("-----------------------------------------------------------------------------------------------");
			default:
				System.out.println("UNKNOWN COMMAND...");
				System.out.println(" ");
				System.out.println(" ");
			}
			
			
			accion = in.next();
			
		} while((this.game.getHighest() != 2048) || (this.game.getBoard().noMoves()));
		// finaliza si:
		//		+noMoves == true
		//		+highest == 2048
		
		this.game.getBoard().noMoves();
		
		in.close();
	}
	
	
	// este metodo puede ser VOID
	public void exMove () {	
		
		
		// pedimos direccion
		Direction dir;
		System.out.print("Command > ");
		String d = in.next();
		
		// tipo dir:
		dir = this.stringToDir(d);
		
		this.game.move(dir);
		
	}
	
	public Direction stringToDir (String s) {	
		//toUpperCase para reconocer siempre
		switch(s.toUpperCase()) {
		case "UP":
			return Direction.UP;
		case "DOWN":
			return Direction.DOWN;
		case "LEFT":
			return Direction.LEFT;
		case "RIGHT":
			return Direction.RIGHT;
		case "":
			System.out.println("Move must be followed by a direction: up, down, right or left...");
			return stringToDir(this.in.next());
			
		default :
			System.out.println("Unknown direction for known command...");
			return stringToDir(this.in.next());
		}
	}
}
