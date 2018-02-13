package tp.pr2.control;

import java.util.Scanner;

import tp.pr2.control.commands.Command;
import tp.pr2.control.commands.CommandParser;
import tp.pr2.control.commands.FileNotExists;
import tp.pr2.control.commands.NotValidName;
import tp.pr2.control.commands.UnknownCommand;
import tp.pr2.logic.multigames.Game;

public class Controller {
	
	
	// atributos privados:
	private Game _game;
	private Scanner _in;

	public Controller (Game game, Scanner in) {
		this._game = game;
		this._in = in;
	}
	public void run (){
			
		boolean exit = false;
		
		// mostramos:		
		System.out.println(this._game.toString());
		
		do{
			try{
				/*pedimos opciï¿½n y dividimos en palabras el texto*/
				System.out.println("Introduzca una opcion(move, reset, exit o help): ");
				String accion;
				
				accion = _in.nextLine();
				String[] parts = accion.split(" ");
				
				
				Command command = CommandParser.parseCommand(parts, this._in);
				if (command != null){
					
					// AL VOLVER DE ESTE EXECUTE EN COMMAND = PLAY
					// EN GAME HABÃ�A LO DE ANTES Y NO EL NUEVO JUEGO
					if(command.execute(_game, _in)){
						System.out.println(this._game.toString());
					}
					// para que a la siguiente si es move se muestre
					
				}
				
			}			
			catch(UnknownCommand u){
				System.out.println(u.getMessage());
			}
			catch(NotValidName n){
				System.out.println(n.getMessage());
			}
			catch(FileNotExists f){
				System.out.println(f.getMessage());
			}
			
						
		} while(!this._game.isFinished() && exit == false);
		
	
				
		_in.close();
	}
	



}
