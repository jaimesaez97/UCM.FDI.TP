package tp.pr3.control.commands;

@SuppressWarnings("serial")
public class UnknownCommand extends Throwable{

	public UnknownCommand(){
		super("Comando desconocido (Solo move, help, reset, exit, play, save o load");
	}
	
}
