package tp.pr3.logic.multigames;

import tp.pr3.util.*;

public enum GameType {

	ORIG("2048, original version", "original", new Rules2048()),
	FIB("2048, Fibonacci version", "fib", new RulesFib()),
	INV("2048, Inverse version", "inverse", new RulesInverse());
	
	private String userFriendlyName;
	private String parameterName;
	private GameRules correspondingRules;
	
	private GameType (String friendly, String param, GameRules rules) {
		userFriendlyName = friendly;
		parameterName = param;
		correspondingRules = rules;
	}
	
	//used on PlayCommand(parse) and Game(load)
	public static GameType parse (String param) {
		for(GameType gameType : GameType.values()) {
			if(gameType.parameterName.equals(param))
				return gameType;
		}
		return null;
	}
	
	public static String externaliseAll () {
		String s = "";
		
		for(GameType gameType : GameType.values()) {
			s += " /Version: " + gameType.parameterName + " ";
		}
		
		return s;
	}
	
	public GameRules getRules() {
		return correspondingRules;
	}
	
	public String externalise () {
		return parameterName;
	}
	
	public String toString () {
		return userFriendlyName;
	}
}
