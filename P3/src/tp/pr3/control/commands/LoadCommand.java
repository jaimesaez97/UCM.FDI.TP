package tp.pr3.control.commands;

import java.io.*;
import java.util.Scanner;

import tp.pr3.logic.multigames.Game;
import tp.pr3.logic.multigames.GameType;
import tp.pr3.util.MyStringUtils;

public class LoadCommand extends Command {
	
	private String _filename;
	
	public LoadCommand (String filename) {
		super("load", "load a saved game");
		this._filename = filename;
	}

	public boolean execute(Game game, Scanner in) {
		//------------------------//
		// HECHO AUTOMATICO ¿?¿?
		//------------------------//
		try {
			File file = new File(_filename);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			// LEO INFO
			br.readLine();
			//br.read();
		GameType type = game.load(br);
		System.out.println("Game successfully loaded from file: " + type.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public Command parse(String[] commandWords, Scanner in) throws NotValidName, FileNotExists {
		if(commandWords[0].equalsIgnoreCase(this.commandName)){
			_filename = commandWords[1];
			if(MyStringUtils.validFileName(_filename)){
				File file = new File(_filename);
				if(file.exists()){
					return this;
				}
				else{
					throw new FileNotExists();
				}
			}
			else {
				throw new NotValidName();
			}
		}
		return null;
	}
}
