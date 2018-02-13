package tp.pr2.control.commands;

@SuppressWarnings("serial")
public class FileNotExists extends Exception{
	public FileNotExists(){
		super("El archivo no existe...");
	}
}
