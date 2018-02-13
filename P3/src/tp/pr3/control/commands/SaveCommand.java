package tp.pr3.control.commands;

import java.io.*;
import java.util.Scanner;

import tp.pr3.logic.multigames.Game;
import tp.pr3.util.MyStringUtils;

public class SaveCommand extends Command {
	
	private String _filename;
		
	public SaveCommand (String filename) {
		super("save", "save a played game");
		this._filename = filename;
	}

	public boolean execute(Game game, Scanner in) {
		//------------------------//
		// HECHO AUTOMATICO ¿?¿?
		//------------------------//
		try {
			File file = new File(_filename);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			// ESCRIBO INFO
			bw.write("The file stores a saved 2048 game");
			bw.write("\n");
			
			game.store(bw);
			System.out.println("Game successfully saved from file: " + game.get_gameType().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Command parse(String[] commandWords, Scanner in) throws NotValidName {
		if(commandWords[0].equalsIgnoreCase(this.commandName)){
			_filename = commandWords[1];			
			
			_filename = this.confirmFileNameStringForWrite(_filename, in);
			
			return this;
		}
		return null;
	}
	
	private String confirmFileNameStringForWrite(String filenameString, Scanner in) throws NotValidName {
		String loadName = filenameString;
		boolean filename_confirmed = false;
		
		while(!filename_confirmed){
			if(MyStringUtils.validFileName(loadName)){
				
				File file = new File(loadName);
				if(!file.exists())
					filename_confirmed = true;
				else{
					loadName = getLoadName(filenameString, in, filename_confirmed);
					filename_confirmed = true;
				}
			}
			else {
				throw new NotValidName();
			}
			
			//--------------------------------------------//
			// DUDA -> HAY QUE ABRIR/CERRAR FICHERO?
			//--------------------------------------------//
		}
		
		return loadName;
		// ESTE NOMBRE ES EL BUENO
	}
	
	private String getLoadName(String filenameString, Scanner in, Boolean filename_confirmed) throws NotValidName{
		
		// SOLO ENTRA AQUI PARA PEDIR EL NOMBRE
		// DE FICHERO CUANDO YA SE HA PEDIDO Y
		// EL FICHERO EXISTIA
		
		String filenameInUseMsg = "The file already exists, do you want to overwrite it? (Y/N)";
		String newFilename = null;
		boolean yesOrNo = false;
		
		while(!yesOrNo){
			System.out.println(filenameInUseMsg + ": ");
			String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
			
			if(responseYorN.length == 1){
				switch(responseYorN[0]){
				case "y" :
					yesOrNo = true;
					filename_confirmed = true;
					return filenameString;
				case "n" :
					yesOrNo = false;
					System.out.println("Escriba el nombre del nuevo fichero...");
					String response = in.nextLine();
					return confirmFileNameStringForWrite(response, in);
					//--------------------------------------------//
					// DUDA -> ¿FUNCIONA BIEN?
					//--------------------------------------------//
				}
			} 
			else{
				throw new NotValidName();
			}
		} 
		
		return newFilename;
	}
}
