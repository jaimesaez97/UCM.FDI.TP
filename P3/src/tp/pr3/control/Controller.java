package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.control.commands.Command;
import tp.pr3.control.commands.CommandParser;
import tp.pr3.control.commands.DirNotValid;
import tp.pr3.control.commands.FileNotExists;
import tp.pr3.control.commands.GameModeInvalid;
import tp.pr3.control.commands.NotValidName;
import tp.pr3.control.commands.UnknownCommand;
import tp.pr3.logic.multigames.Game;

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
			catch(DirNotValid e){
				System.out.println(e.getMessage());
			}
			catch (GameModeInvalid ex){
				System.out.println(ex.getMessage());

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
