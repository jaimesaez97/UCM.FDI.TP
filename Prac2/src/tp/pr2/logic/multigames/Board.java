package tp.pr2.logic.multigames;

import tp.pr2.util.GameRules;

import java.io.*;

public class Board {
	// atributos privados:
	private Cell[][] _board;
	private int _boardSize;
	private int _highest;
	
	//constructora:
	public Board (int _boardSize) {
		super();
		this._boardSize = _boardSize;
		this._board = new Cell[this._boardSize][this._boardSize];
		this._highest = 0;
		
		for(int i = 0; i < this._boardSize; ++i) {
			for(int j = 0; j < this._boardSize; ++j){
				Position g = new Position(i, j);
				this._board[i][j] = new Cell (0, g);
			}
		}
		
	}
	
	public Cell[][] getBoard () {
		return this._board;
	}
	
	public Cell getCell (int x, int y) {
		return this._board[x][y];
	}
	
	public void setCell (Position pos, int value) {
		this._board[pos.getX()][pos.getY()].setValue(value);
	}
	
	public void store(BufferedWriter bw) throws IOException {
		for(int i = 0; i < this._boardSize; ++i) {
			for(int j = 0; j < this._boardSize; ++j){
				bw.write(this._board[i][j].getValue() + " ");
			}
			bw.write("\n");
		}
	}
	
	public void load (BufferedReader br, GameType type) throws IOException{
		String linea = br.readLine();

		String[] split = linea.split(" ");
		
		
		
		this._boardSize = split.length;

		//br.reset();
		// YA TENGO EL TAMAÑO Y ESTOY AL PRINCIPIO DEL TABLERO

		
		for(int i = 0; i < this._boardSize; ++i){
			for(int x = 0; x < this._boardSize; ++x){
				int c = Integer.parseInt(split[x]);
				this._board[i][x].setValue(c);
				
				if(c == type.getRules().highest(this._highest, c))
					this._highest = c;
			}
			if(i < 3){
				linea = br.readLine();
				split = linea.split(" ");
			}
		}
		
	}
	
	public boolean isFull() {
		for(int i = 0; i < this._boardSize; ++i) {
			for(int j = 0; j < this._boardSize; ++j) {
				if(this._board[i][j].isEmpty())
					return false;
			}
		}
		return true;
	}
		
	public boolean noMoves(GameRules rules) {
		
		boolean noMoves = true;
		
		Cell vecina;
		int cont = 0;
		Cell[][] AUXi = new Cell[this._boardSize][this._boardSize];

		for(int i = 0; i < this._boardSize; ++i) {
			for(int j = 0; j < this._boardSize; ++j){
				Position g = new Position(i, j);
				AUXi[i][j] = new Cell (0, g);
			}
		}
		
		while (cont < 4){
			
		
		for(int i = 1; i < this._boardSize; ++i) {
			for(int j = 0; j < this._boardSize; ++j) {
				
				//vecina = AUXi[this._board[i][j].getPos().vecina().getX()][this._board[i][j].getPos().vecina().getY()];
				// guardamos la vecina
				vecina = this._board[this._board[i][j].getPos().vecina().getX()][this._board[i][j].getPos().vecina().getY()];
				
				// se supone isFull()
				if(rules.canMerge(AUXi[i][j], vecina)){
					noMoves = false;
				}
			}
		}
		rotation (AUXi);
		cont++;
	}
		
		
		
		return noMoves && isFull();	// si pongo return isFull():
							//		-NO SE PUEDE MOVER: 
							// 			+SI EL TABLERO LLENO -> PERDIDO
							//			+SI TABLERO NO LLENO -> CONTINUE
	}
	public void rotation (Cell[][] AUX){
		
		
		
			for ( int i = 0; i < this._boardSize; ++i){
				int  max = (this._boardSize) - 1;
				for(int j = 0; j < this._boardSize; ++j){
					
					AUX[max][i].setValue(this._board[i][j].getValue());
					max--;
				}
			}
			
			for(int i = 0; i < this._boardSize; ++i){
				for ( int j = 0 ; j < this._boardSize; j++){
					this._board[i][j].setValue(AUX[i][j].getValue());
				}
			}
		
		
	}
	
	
	public MoveResults executeMove1 (Direction dir, int points, GameRules rules) {
		Cell vecina;
		
		boolean moved = false;
		
		int union, rotates = 0;
		Cell[][] AUX = new Cell[this._boardSize][this._boardSize];

		for(int i = 0; i < this._boardSize; ++i) {
			for(int j = 0; j < this._boardSize; ++j){
				Position g = new Position(i, j);
				AUX[i][j] = new Cell (0, g);
			}
		}
		
		switch(dir){
			case RIGHT:
				rotates = 1;
				break;
			case DOWN:
				rotates = 2;
				break;
			case LEFT:
				rotates = 3;
				break;
			case UP:
				rotates = 0;
		default:
			rotates = 0;
		
		}
			int cont = 0;
			while (cont < rotates){
			rotation(AUX);
			
			cont++;
			}
		//DESPLAZAR
		for(int i = 1; i < this._boardSize; ++i){
			for(int j = 0; j < this._boardSize; ++j){
				vecina = this._board[this._board[i][j].getPos().vecina().getX()][this._board[i][j].getPos().vecina().getY()];
				boolean cambio = false;

				int auxI = i;
				if(this._board[vecina.getPos().getX()][vecina.getPos().getY()].isEmpty()){
					moved = true;
					while (this._board[vecina.getPos().getX()][vecina.getPos().getY()].isEmpty() && (i > 0)) {

						this._board[vecina.getPos().getX()][vecina.getPos().getY()].setValue(this._board[i][j].getValue());
						this._board[i][j].setValue(0);
						--i;
						// redefino vecina si hay hueco
						if(i == 0) {
							// para que no pete en la redefinicion
							i++;
							
							cambio = true;
						}
						vecina = this._board[this._board[i][j].getPos().vecina().getX()][this._board[i][j].getPos().vecina().getY()];
						if(cambio == true)
							i--;
						
					
					}
					
					cambio = false;
				}
				
				i = auxI;
				
			}	
		}
		//--------------------------------------------------------------------------------------------------------------
		//FUSIONAR
		for(int i = 1; i < this._boardSize; ++i){
			for(int j = 0; j < this._boardSize; ++j){
				vecina = this._board[this._board[i][j].getPos().vecina().getX()][this._board[i][j].getPos().vecina().getY()];
	
				
				if(this._board[i][j].getValue() != 0 && !this._board[vecina.getPos().getX()][vecina.getPos().getY()].isEmpty()){
					
					if(this._board[i][j].canMerge(vecina, rules)) {
						
						moved = true;
						// DOMERGE -> SE PONE EN VECINA
						union = this._board[i][j].doMerge(this._board[vecina.getPos().getX()][vecina.getPos().getY()], rules);
						if(union != 0){
							this._board[vecina.getPos().getX()][vecina.getPos().getY()].setValue(union);
						}
						
						// actualizamos SCORE (points)
						points += this._board[vecina.getPos().getX()][vecina.getPos().getY()].getValue();
						
						//ponemos a 0 board[i][j]
						this._board[i][j].setValue(0);
						
						// actualizamos HIGHEST (maxToken)
						_highest = rules.highest(_highest, this._board[vecina.getPos().getX()][vecina.getPos().getY()].getValue());
						
//						if((this._board[vecina.getPos().getX()][vecina.getPos().getY()].getValue()) > highest){
//							
//							highest = this._board[vecina.getPos().getX()][vecina.getPos().getY()].getValue();
//						}
						int auxI = i;
						while(i < _boardSize - 1){
							if(this._board[i][j].isEmpty()) {
								// si [i][j] está vacía traigo todo el resto
								this._board[i][j].setValue(this._board[i + 1][j].getValue());
								this._board[i + 1][j].setValue(0);
							}
							i++;
							
						}
						i = auxI;
					}
				}	
			}
		}
		
		//FUNCION ROTACION
		
		
		
		while (cont < 4){
			rotation(AUX);
		
			cont++;
			}
	
	
		MoveResults move = new MoveResults(moved, points);
		
		return move;
	}
	
	public String toString () {
		String ret = "";
		
		
		int cellSize = 7;
		String space = " ";
		String vDelimiter = "|";
		String hDelimiter = "-";
		
		// delimitador:
		ret += " ";
		for(int x = 0; x < (this._boardSize*(cellSize - 1)) - 1; ++x) {
			ret += hDelimiter;
		}
		ret += " \n";
		
		for(int i = 0; i < this._boardSize; ++i) {	// habria que hacer un GETTER de _boardSize?
			for(int j = 0; j < this._boardSize; ++j) {
				
				ret += vDelimiter + space + space ; 
				if( this._board[i][j].getValue() == 0){
					ret +=  space + space + space;
				}
				else{
					ret += String.valueOf(this._board[i][j].getValue()) + space + space;
				}
			}
			
			ret += vDelimiter + "\n";
			// delimitador:
			ret += " ";
			for(int x = 0; x < (this._boardSize*(cellSize - 1))- 1; ++x) {
				ret += hDelimiter;
			}
			ret += " \n";
		}
				
		return ret;
	}
	
	public void setSize(int size){
		this._boardSize = size;
	}
		
	public int[][] getState(){
		int[][] aState = new int[this._boardSize][this._boardSize];
		
		
		for(int i = 0; i < this._boardSize; ++i){
			for(int j = 0; j < this._boardSize; ++j){
				aState[i][j] = this._board[i][j].getValue();
			}
		}
		
		return aState;
	}
	
	
//	public void setHighest(int x){
//		this._highest = x;
//	}
		
	public int getHighest(){
		return this._highest;
	}
	
	public void setState(int[][] aState){
		for(int i = 0; i < this._boardSize; ++i){
			for(int j = 0; j < this._boardSize; ++j){
				this._board[i][j].setValue(aState[i][j]); 
			}
		}
	}
	
	public int getSize(){
		return this._boardSize;
	}



	public void setHighest(int i) {
		// TODO Auto-generated method stub
		this._highest = i;
	}
}
	