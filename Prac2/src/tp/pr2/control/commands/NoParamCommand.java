package tp.pr2.control.commands;

import java.util.Scanner;

/*POR ESTA CLASE PASAN HELP EXIT Y RESET*/
/*Es noparams porque solo tienen una palabra(move tiene la direccion tb)
 * La constructora :
 * 	NECESITA LOS PARAMETROS COMMAND NAME Y HELPTEXT
 * Si no se los pasamos no podemos construir un comando(mirar constructora de command solo necesita dos par�metros)*/

public abstract class NoParamCommand extends Command{

	public NoParamCommand(String commandName, String helpText) {
		
		super(commandName, helpText);
		
	}
	/*Al parse le llega el input, en la casila cero es lo que necesitamos para devolver el objeto, es sin parametros
	 * y devolvemos this porque este metodo lo invocaremos con un objeto comando(el que vayamos necesitando)*/
public  Command parse(String[] commandWords, Scanner in){
		
		if(commandWords[0].equalsIgnoreCase(this.commandName) && commandWords.length == 1){
				return this;
		}
		else
			return null;

	}

	

	
}
