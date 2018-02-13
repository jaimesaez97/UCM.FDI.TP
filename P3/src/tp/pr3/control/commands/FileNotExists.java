package tp.pr3.control.commands;

@SuppressWarnings("serial")
public class FileNotExists extends Exception{
	public FileNotExists(){
		super("El archivo no existe...");
	}
}
