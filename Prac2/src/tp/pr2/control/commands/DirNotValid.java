package tp.pr2.control.commands;

@SuppressWarnings("serial")
public class DirNotValid extends Exception {

	public DirNotValid(){
		super("La direccion introducida no es valida( Solo up, down, left o right)");
	}
	
}
