package tp.pr3.logic.multigames;

import tp.pr3.util.GameRules;

public class Cell {
	// ACABDO
	
	
	// atributos privados:
	private int value;
	private Position pos;
	
	public Cell (int value, Position pos) {
		super();
		this.value = value;
		this.pos = new Position(pos.getX(), pos.getY());
	}
	
	public boolean isEmpty(){	// si está vacía devuelve true
		if(this.getValue() == 0)
			return true;
		return false;
	}
	
	public int doMerge (Cell neighbour, GameRules rules) {			// si el valor de la celda 1 concide con el de la vecina
		return rules.merge(neighbour, this); 
	}
	
	public boolean canMerge(Cell neighbour, GameRules rules) {
		return rules.canMerge(neighbour, this);
	}
	
	
	// getters:
	public int getValue() {
		return this.value;
	}
	
	public Position getPos() {
		return this.pos;
	}
	
	// setters:
	public void setValue (int value) {
		this.value = value;
	}
	
	public void setPos (Position pos) {
		this.pos = pos;
	}
}
