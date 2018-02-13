package tp.pr2.logic.multigames;

import java.util.EmptyStackException;



public class GameStateStack {
	// atributos privados
	private static final int _CAPACITY = 20;
	private GameState[] _buffer = new GameState[_CAPACITY+1];
	private int _tam = 0; 
	
	// m�todos p�blicos
	public GameState pop(){ // Devuelve el último estado almacenado
		_tam--;
		GameState gs;
		gs = this._buffer[_tam];
		this._buffer[_tam] = null;
		return gs;
	}
	
	public void push (GameState state){ // Almacena un nuevo estado
		
		if(_tam < _CAPACITY){
			this._buffer[_tam] = state;
			this._tam++;
		}
		
		else{ // corro todo a la izq y meto state en _buffer[tam-1]
			
			// �_tam-2 o -1?
			for(int i = 0; i < _tam - 2; ++i){
				this._buffer[i] = _buffer[i + 1];
			
			}
			this._buffer[_tam - 1] = state;
		}
	}
	
	public void vaciaStack(GameStateStack gs){
		int tam = gs._tam;
		for(int i = tam; i >= 0; i--){
		gs._buffer[i] = null;


		}
		gs._tam = 0;
		}
	
	public boolean isEmpty() throws EmptyStackException{ 
		if(_tam == 0)
			throw new EmptyStackException();
		
		return false;
	}
	
}
