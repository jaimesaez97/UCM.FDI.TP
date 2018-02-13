package tp.pr3.util;

import java.util.Random;

import tp.pr3.logic.multigames.Board;
import tp.pr3.logic.multigames.Cell;
import tp.pr3.logic.multigames.Position;

public class RulesInverse implements GameRules {
	
	final int STOP_VALUE = 1;
	
		public boolean canMerge (Cell self, Cell other){
			return self.getValue() == other.getValue();
		}
	
		public void addNewCellAt(Board board, Position pos, Random rand){				
			board.setCell(pos, (rand.nextInt(100) < 10 ? 1024 : 2048));
		}
		
		public int merge(Cell self, Cell other){
			if(self.getValue() == other.getValue())
				return self.getValue() / 2;
			else return 0;
		}
		
		public int highest (int actual, int nuevo) {

			if(actual == 0 || nuevo == 0){
				return 2048;
			}
			if(actual < nuevo)
				return actual;
			
			 if ( nuevo < actual)
					return nuevo;
			
			 return actual;
		
		
		}

		public int getWinValue(Board board) {
			return STOP_VALUE;
		}

		public boolean win (Board board) {
			return board.getHighest() == this.getWinValue(board); // GET_HIGHEST O GET_MIN?
		}
}
