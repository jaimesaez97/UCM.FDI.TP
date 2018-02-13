package tp.pr3.util;

import tp.pr3.logic.multigames.Board;
import tp.pr3.logic.multigames.Cell;
import tp.pr3.logic.multigames.Position;

import java.util.Random;

public interface GameRules {
	
	// METODOS NO IMPLEMENTADOS:
	void addNewCellAt(Board board, Position pos, Random rand);//Incorpora una nueva celda con valor 
															  //aleatorio en la posición pos del tablero board	
	int merge(Cell self, Cell other);	//fusiona dos celdas y devuelve
										//el número de puntos obtenidos	
	int getWinValue(Board board);	// Devuelve el mejor valor de tablero
									//según las reglas del juego, comprobando si es un valor 
									//ganador y si se ha ganado el juego,	
	boolean win (Board board); 	// Devuelve si el juego se ha ganado o no
	

	int highest (int actual, int nuevo);
	
	boolean canMerge (Cell self, Cell other); 	// FUSIONAR dependiendo de celda
	
	// METODOS DEFAULT
	default boolean lose (Board board){ // devuelve si se ha perdido o no 
		return board.noMoves(this);
		//TRUE-> SE HA PERDIDO
	}
	
	default Board createBoard (int size) {
		return new Board(size);
	}
	
	default void addNewCell(Board board, Random rand){
		// Elige una posición libre de board e invoca el método        
		// addNewCellAt() para añadir una celda en esa posición.
	
		while(true){
			int x = rand.nextInt(board.getSize());
			int i = rand.nextInt(board.getSize());
			Position pos = new Position(x, i);
	
			if(board.getBoard()[x][i].isEmpty()){
				addNewCellAt(board, pos, rand);
				return;
			}
		}
	}
	
	default void initBoard(Board board, int numCells, Random rand)   {
		// Inicializa board eligiendo numCells posiciones   
		// libres, e invoca el método addNewCellAt() para 
		// añadir nuevas celdas en esas posiciones
		while(numCells > 0){
			addNewCell(board, rand);
			numCells--;
		}
	}

	
}
