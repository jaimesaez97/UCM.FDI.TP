package tp.pr3.util;

import java.util.Random;

import tp.pr3.logic.multigames.Board;
import tp.pr3.logic.multigames.Cell;
import tp.pr3.logic.multigames.Position;


public class RulesFib implements GameRules {
	final int STOP_VALUE = 144;
	
	// MIRAR COMP PARA FIBONACCI
	public boolean canMerge (Cell self, Cell other){
		if(self.isEmpty() || other.isEmpty()){
			return false;
		}
		
		if(self.getValue() > other.getValue()){
			if(self.getValue() == MyMathsUtil.nexFib(other.getValue()))
				return true;
		}
		if (self.getValue() < other.getValue()){
			if(other.getValue() == MyMathsUtil.nexFib(self.getValue()))
				return true;
		}
		if(self.getValue() == 1 && other.getValue() == 1){
			return true;
		}
		return false;
	}
	
	public void addNewCellAt(Board board, Position pos, Random rand){
		board.setCell(pos, (rand.nextInt(100) < 10 ? 2 : 1));
	}
	
	public int merge(Cell self, Cell other){
		return self.getValue() + other.getValue();
	}
	
	public int highest (int actual, int nuevo) {

		if(actual > nuevo )
			return actual;
		
		 if ( nuevo > actual )
				return nuevo;
				
		return actual;
	
	}
	
	public int getWinValue(Board board){
		return STOP_VALUE;
	}
	
	public boolean win(Board board){
		return board.getHighest() == getWinValue(board);
	}
}
