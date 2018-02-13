package tp.pr2.logic.multigames;


import java.io.*;

import tp.pr2.util.GameRules;

import java.util.EmptyStackException;
import java.util.Random;

import tp.pr2.logic.multigames.GameState;
import tp.pr2.logic.multigames.GameStateStack;
import tp.pr2.util.RulesFib;
import tp.pr2.util.Rules2048;
import tp.pr2.util.RulesInverse;

public class Game {
	
	// FALTA TOSTRING

	// atributos privados:
	
	private Board _board;
	private int _size;	// boardSize
	private int _initCells;
	private Random _myRandom;
	
	private int _highest;
	private int _score;
	private boolean _finish;
	
	private GameRules _rules;
	
	private GameStateStack _undoStack = new GameStateStack();
	private GameStateStack _redoStack = new GameStateStack();
	
	private GameType _gameType;
	
	// constructora:	
	public Game (int size, int initCells, Random random, GameRules rules, GameType gameType) {
		this._size = size;
		this._initCells = initCells;
		this._myRandom = random;
		_board = new Board(_size);	
		
		this._score = 0;
		this._finish = false;
		
		for(int i = 0; i < initCells; ++i){
			rules.addNewCell(this._board, this._myRandom);
		}
		// meto las reglas:
		this._rules = rules;

			
		this._highest = this.getFirstHigh();
		
		this._gameType = gameType;
	} 
	
	public int getFirstHigh(){
		int high = this.getBoard().getCell(0, 0).getValue();
		for(int i = 0; i <= this.getBoard().getSize() - 1;i++){
			for(int j = 0; j <= this.getBoard().getSize() - 2;j++ ){
				high = this._rules.highest(high,this.getBoard().getCell(i, j+1).getValue());
			}
		}
		return high;
	}
	
	public void playCommand(Game game, int size, int initCells, Random rand, GameType type) {
		game.getBoard().setSize(size);
		//game.getBoard().setHighest(0);
		
		// VACIAMOS STACKS
		this.getRedoStack().vaciaStack(getRedoStack());
		this.getUndoStack().vaciaStack(getUndoStack());
		
		this.set_score(0);
		switch(type){
		case ORIG:	
			game.getBoard().setHighest(0);
			game._rules = new Rules2048();
			game._gameType = GameType.ORIG;
			game._initCells = initCells;
			game._size = size;
			game._finish = false;
			break;
		case INV:
			game.getBoard().setHighest(2048);
			game._rules = new RulesInverse();
			game._gameType = GameType.INV;
			game._initCells = initCells;
			game._size = size;
			game._finish = false;
			break;
		case FIB:
			game.getBoard().setHighest(0);
			game._rules = new RulesFib();
			game._gameType = GameType.FIB;
			game._initCells = initCells;
			game._size = size;
			game._finish = false;
			break;
		default:
			System.out.println("No se reconoce el juego");
		}
		
		for(int x = 0; x < initCells; ++x){
			game._rules.addNewCell(game.getBoard(), rand);
		}
		
		
				
		this._highest = this.getFirstHigh();
		
	}
	
	public void store(BufferedWriter bw) throws IOException {
		
		
		try {	
			// ESCRIBO TABLERO
			this._board.store(bw);
			
			// ESCRIBO LINEA EN BLANCO
			bw.write("\n");
			
			// ESCRIBO INITCELLS, SCORE Y TIPO
			bw.write(this._initCells + " " );
			bw.write(this._score + " " );
			bw.write(this._gameType.externalise() + "\n");
			
			bw.write("\n");
			
			// CIERRO ARCHIVO
			bw.close();
		}
		catch (Exception e){
			System.out.println("No se ha podido completar el guardado...");
		}
	}
	
	public GameType load(BufferedReader br) throws IOException{
		// fileName ES VÃ�LIDO
		//String linea = "";
		
		// VACIAMOS STACKS
				this.getRedoStack().vaciaStack(getRedoStack());
				this.getUndoStack().vaciaStack(getUndoStack());

		try {
			String liena = br.readLine();
			// CARGO TABLERO	
			this._board.load(br, this._gameType);
			
			liena = br.readLine();
			liena = br.readLine();
			
			String[] split = liena.split(" ");
			// CARGO INITCELLS
			this._initCells = Integer.parseInt(split[0]);
			
			// CARGO SCORE
			this._score = Integer.parseInt(split[1]);
			
			// CARGO TYPO_JUEGO
			this._gameType = GameType.parse(split[2]);
			
			// CARGO REGLAS
			this._rules = this._gameType.getRules();
			//-------------------------------//
			// 	Â¿HAY QUE CARGAR REGLAS?
			//-------------------------------//
			
			// CARGO HIGHEST
			this._highest = this.getBoard().getHighest();
			
			// CIERRO ARCHIVO
			br.close();
						
			return this._gameType;
			//-------------------------------//
			// 	PONE ESTO PERO ESTA BIEN?
			//-------------------------------//
			
		}
			
		catch (Exception e) {
			System.out.println("No se ha podido completar la carga...");
		} 
		
		
		return this._gameType;
	}
	
	public Board getBoard () {
		return this._board;
	}
	
	public String toString () {
		String ret = "";
		
		//LLAMADA A MOSTRAR BOARD
		ret = this._board.toString();
		
		// en la primera vuelta no hay highest		
		//metemos resto:
		
		ret += "  Record: " + this._highest + "            score: " + this._score;
		
		ret += "\n" + "\n" + "\n";
				
		if(this._finish) {
			if(this._rules.win(this.getBoard())) { //Â¿constante? 
				ret += ("	   Has llegado a "+ _rules.getWinValue(this.getBoard()) +"	!!!");
				ret += ("\n            Enhorabuena !!");
				ret += ("\n");
			}
			else{
				ret += ("	   No hay movimientos posibles...");
				ret += ("\n                    GAME OVER     ");
				ret += ("\n");
			}
				
		}	
		
		return ret;		
	}

	public void move(Direction dir) {
			
		this._undoStack.push(this.getState());
		
		MoveResults mo = new MoveResults(false, this._score);
		
		mo = this._board.executeMove1(dir, this._score, this._rules);
		
		// si se ha movido:
		if(mo.isMoved()) {
			// actualizo HIGHEST y SCORE
			this._score = mo.getPoints();
			
			
			//if(this.getBoard().getHighest() > this._highest)
			
				// PREGUNTA: ï¿½porque hay que hacer esto?
			//	this._highest = this.getBoard().getHighest();
			
			
			this._highest = this._rules.highest(this.getBoard().getHighest(), this._highest);
			_rules.addNewCell(_board, _myRandom);
			
			if(this._rules.win(this.getBoard()))
				this.setFinished();
		}
		if(this._rules.lose(this.getBoard()))
			this.setFinished();
	}
	
	public void reset() {
		this.getRules().addNewCell(this._board, this._myRandom);
		this._highest = this.getBoard().getHighest();
		this.set_score(0);
		this._board = new Board(this._size);
	
	}
	
	// undo & redo
	
public void undo() {
		
		//ESTO YA NO HACE FALTA(TRATAMOS LA PILA VACIA CON EXCEPCIONES
		/*
		if(this.getUndoStack().isEmpty()){
			System.out.println("No hay movimientos para deshacer");
		}
				
		else {
		*/
		try{
			if(!this.getUndoStack().isEmpty())
				this._redoStack.push(this.getState());
			
			setState(this._undoStack.pop());
			this._highest = this.getFirstHigh();
		}
		catch(EmptyStackException e){
			System.out.println("No hay movimientos para deshacer");
		}
		
	}
public void redo() {
	/*
	if(this.getRedoStack().isEmpty()){
		System.out.println("No hay movimientos para rehacer");
	}
	else{
	*/
	try{
		if(!this.getRedoStack().isEmpty())
			this._undoStack.push(this.getState());
		setState(this._redoStack.pop());
		this._highest = this.getFirstHigh();
	}
	
	catch(EmptyStackException e){
		System.out.println("No hay movimientos para rehacer");
	}
}

//	  NO TIRA:
//	public Game changeGame(int size, int initCells, Random rand, GameRules rules){
//		return new Game(size, initCells, rand, rules);
//	}
	
	// getters & setters:
	
	public GameState getState() {
		// ï¿½es construyendo?
		return new GameState(this._board.getState(), this._score, this._highest);

	}
	
	public void setState(GameState aState){ // Restablece el juego al estado aState e 
										    // invocando el mï¿½todo setState de Board
		this._board.setState(aState.get_boardState());
		
		
		this._score = aState.get_score();
	}
	
	public boolean isFinished() {
		return this._finish;
	}
	
	public int getHighest() {
		return this._highest;
	}
	
//	public void setHighest(int x) {
//		this._highest = x;
//	}
	
	public int getScore() {
		return this._score;
	}

	public int get_initCells() {
		return _initCells;
	}

	public void set_score(int _score) {
		this._score = _score;
	}

	public void setFinished() {
		this._finish = true;
		
	}

	public GameStateStack getUndoStack() {
		return _undoStack;
	}

	public GameStateStack getRedoStack() {
		return _redoStack;
	}
	
	public GameRules getRules() {
		return this._rules;
	}
		
}
